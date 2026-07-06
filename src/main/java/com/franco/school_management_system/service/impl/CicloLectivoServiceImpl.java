package com.franco.school_management_system.service.impl;

import com.franco.school_management_system.dto.CicloLectivoRequest;
import com.franco.school_management_system.dto.CicloLectivoResponse;
import com.franco.school_management_system.entity.CicloLectivo;
import com.franco.school_management_system.exception.ResourceNotFoundException;
import com.franco.school_management_system.repository.CicloLectivoRepository;
import com.franco.school_management_system.service.interfaces.CicloLectivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CicloLectivoServiceImpl implements CicloLectivoService {

    private final CicloLectivoRepository cicloLectivoRepository;

    @Override
    public List<CicloLectivoResponse> findAll() {
        return cicloLectivoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CicloLectivoResponse findById(Long id) {
        return toResponse(getCicloLectivoById(id));
    }

    @Override
    public CicloLectivoResponse create(CicloLectivoRequest request) {
        CicloLectivo cicloLectivo = new CicloLectivo();

        cicloLectivo.setAnio(request.anio());
        cicloLectivo.setFechaInicio(request.fechaInicio());
        cicloLectivo.setFechaFin(request.fechaFin());
        cicloLectivo.setEstado(request.estado());

        return toResponse(cicloLectivoRepository.save(cicloLectivo));
    }

    @Override
    public CicloLectivoResponse update(Long id, CicloLectivoRequest request) {
        CicloLectivo cicloLectivo = getCicloLectivoById(id);

        cicloLectivo.setAnio(request.anio());
        cicloLectivo.setFechaInicio(request.fechaInicio());
        cicloLectivo.setFechaFin(request.fechaFin());
        cicloLectivo.setEstado(request.estado());

        return toResponse(cicloLectivoRepository.save(cicloLectivo));
    }

    @Override
    public void delete(Long id) {
        CicloLectivo cicloLectivo = getCicloLectivoById(id);
        cicloLectivoRepository.delete(cicloLectivo);
    }

    private CicloLectivo getCicloLectivoById(Long id) {
        return cicloLectivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ciclo lectivo no encontrado con id: " + id));
    }

    private CicloLectivoResponse toResponse(CicloLectivo cicloLectivo) {
        return new CicloLectivoResponse(
                cicloLectivo.getId(),
                cicloLectivo.getAnio(),
                cicloLectivo.getFechaInicio(),
                cicloLectivo.getFechaFin(),
                cicloLectivo.getEstado(),
                cicloLectivo.getCreatedAt(),
                cicloLectivo.getUpdatedAt()
        );
    }
}
