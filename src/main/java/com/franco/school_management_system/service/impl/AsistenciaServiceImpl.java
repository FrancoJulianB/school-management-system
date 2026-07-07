package com.franco.school_management_system.service.impl;

import com.franco.school_management_system.dto.AsistenciaRequest;
import com.franco.school_management_system.dto.AsistenciaResponse;
import com.franco.school_management_system.entity.Asistencia;
import com.franco.school_management_system.entity.Inscripcion;
import com.franco.school_management_system.exception.BusinessException;
import com.franco.school_management_system.exception.ResourceNotFoundException;
import com.franco.school_management_system.repository.AsistenciaRepository;
import com.franco.school_management_system.repository.InscripcionRepository;
import com.franco.school_management_system.service.interfaces.AsistenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AsistenciaServiceImpl implements AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    private final InscripcionRepository inscripcionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AsistenciaResponse> findAll() {
        return asistenciaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AsistenciaResponse findById(Long id) {
        return toResponse(getAsistenciaById(id));
    }

    @Override
    public AsistenciaResponse create(AsistenciaRequest request) {
        Inscripcion inscripcion = getInscripcionById(request.inscripcionId());

        asistenciaRepository.findByInscripcionIdAndFecha(inscripcion.getId(), request.fecha())
                .ifPresent(existing -> {
                    throw new BusinessException("Ya existe un registro de asistencia para esa inscripción y fecha.");
                });

        Asistencia asistencia = new Asistencia();
        asistencia.setInscripcion(inscripcion);
        asistencia.setFecha(request.fecha());
        asistencia.setPresente(request.presente());
        asistencia.setObservaciones(request.observaciones());

        return toResponse(asistenciaRepository.save(asistencia));
    }

    @Override
    public AsistenciaResponse update(Long id, AsistenciaRequest request) {
        Asistencia asistencia = getAsistenciaById(id);
        Inscripcion inscripcion = getInscripcionById(request.inscripcionId());

        asistenciaRepository.findByInscripcionIdAndFecha(inscripcion.getId(), request.fecha())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new BusinessException("Ya existe un registro de asistencia para esa inscripción y fecha.");
                });

        asistencia.setInscripcion(inscripcion);
        asistencia.setFecha(request.fecha());
        asistencia.setPresente(request.presente());
        asistencia.setObservaciones(request.observaciones());

        return toResponse(asistenciaRepository.save(asistencia));
    }

    @Override
    public void delete(Long id) {
        asistenciaRepository.delete(getAsistenciaById(id));
    }

    private Asistencia getAsistenciaById(Long id) {
        return asistenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asistencia no encontrada con id: " + id));
    }

    private Inscripcion getInscripcionById(Long id) {
        return inscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada con id: " + id));
    }

    private AsistenciaResponse toResponse(Asistencia asistencia) {
        return new AsistenciaResponse(
                asistencia.getId(),
                asistencia.getInscripcion().getId(),
                asistencia.getInscripcion().getAlumno().getId(),
                asistencia.getInscripcion().getAlumno().getNombre(),
                asistencia.getInscripcion().getAlumno().getApellido(),
                asistencia.getFecha(),
                asistencia.getPresente(),
                asistencia.getObservaciones(),
                asistencia.getCreatedAt(),
                asistencia.getUpdatedAt()
        );
    }
}
