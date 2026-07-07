package com.franco.school_management_system.service.impl;

import com.franco.school_management_system.dto.CalificacionRequest;
import com.franco.school_management_system.dto.CalificacionResponse;
import com.franco.school_management_system.entity.Calificacion;
import com.franco.school_management_system.entity.CursoMateria;
import com.franco.school_management_system.entity.Inscripcion;
import com.franco.school_management_system.exception.ResourceNotFoundException;
import com.franco.school_management_system.repository.CalificacionRepository;
import com.franco.school_management_system.repository.CursoMateriaRepository;
import com.franco.school_management_system.repository.InscripcionRepository;
import com.franco.school_management_system.service.interfaces.CalificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalificacionServiceImpl implements CalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final InscripcionRepository inscripcionRepository;
    private final CursoMateriaRepository cursoMateriaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CalificacionResponse> findAll() {
        return calificacionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CalificacionResponse findById(Long id) {
        return toResponse(getCalificacion(id));
    }

    @Override
    public CalificacionResponse create(CalificacionRequest request) {

        Calificacion calificacion = new Calificacion();

        calificacion.setInscripcion(getInscripcion(request.inscripcionId()));
        calificacion.setCursoMateria(getCursoMateria(request.cursoMateriaId()));
        calificacion.setNota(request.nota());
        calificacion.setTipoEvaluacion(request.tipoEvaluacion());
        calificacion.setFecha(request.fecha());
        calificacion.setObservaciones(request.observaciones());

        return toResponse(calificacionRepository.save(calificacion));
    }

    @Override
    public CalificacionResponse update(Long id, CalificacionRequest request) {

        Calificacion calificacion = getCalificacion(id);

        calificacion.setInscripcion(getInscripcion(request.inscripcionId()));
        calificacion.setCursoMateria(getCursoMateria(request.cursoMateriaId()));
        calificacion.setNota(request.nota());
        calificacion.setTipoEvaluacion(request.tipoEvaluacion());
        calificacion.setFecha(request.fecha());
        calificacion.setObservaciones(request.observaciones());

        return toResponse(calificacionRepository.save(calificacion));
    }

    @Override
    public void delete(Long id) {
        calificacionRepository.delete(getCalificacion(id));
    }

    private Calificacion getCalificacion(Long id){
        return calificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calificación no encontrada con id: " + id));
    }

    private Inscripcion getInscripcion(Long id){
        return inscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada con id: " + id));
    }

    private CursoMateria getCursoMateria(Long id){
        return cursoMateriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CursoMateria no encontrada con id: " + id));
    }

    private CalificacionResponse toResponse(Calificacion c){

        return new CalificacionResponse(
                c.getId(),
                c.getInscripcion().getId(),
                c.getInscripcion().getAlumno().getId(),
                c.getInscripcion().getAlumno().getNombre(),
                c.getInscripcion().getAlumno().getApellido(),
                c.getCursoMateria().getId(),
                c.getCursoMateria().getMateria().getNombre(),
                c.getCursoMateria().getDocente().getNombre() + " " + c.getCursoMateria().getDocente().getApellido(),
                c.getNota(),
                c.getTipoEvaluacion(),
                c.getFecha(),
                c.getObservaciones(),
                c.getCreatedAt(),
                c.getUpdatedAt()
        );
    }

}
