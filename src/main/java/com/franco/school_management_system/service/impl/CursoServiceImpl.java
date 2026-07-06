package com.franco.school_management_system.service.impl;

import com.franco.school_management_system.dto.CursoRequest;
import com.franco.school_management_system.dto.CursoResponse;
import com.franco.school_management_system.entity.Curso;
import com.franco.school_management_system.exception.ResourceNotFoundException;
import com.franco.school_management_system.repository.CursoRepository;
import com.franco.school_management_system.service.interfaces.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    @Override
    public List<CursoResponse> findAll() {
        return cursoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CursoResponse findById(Long id) {
        return toResponse(getCursoById(id));
    }

    @Override
    public CursoResponse create(CursoRequest request) {
        Curso curso = new Curso();

        curso.setNombre(request.nombre());
        curso.setNivel(request.nivel());
        curso.setGradoAnio(request.gradoAnio());
        curso.setDivision(request.division());
        curso.setCapacidad(request.capacidad());

        return toResponse(cursoRepository.save(curso));
    }

    @Override
    public CursoResponse update(Long id, CursoRequest request) {
        Curso curso = getCursoById(id);

        curso.setNombre(request.nombre());
        curso.setNivel(request.nivel());
        curso.setGradoAnio(request.gradoAnio());
        curso.setDivision(request.division());
        curso.setCapacidad(request.capacidad());

        return toResponse(cursoRepository.save(curso));
    }

    @Override
    public void delete(Long id) {
        Curso curso = getCursoById(id);
        cursoRepository.delete(curso);
    }

    private Curso getCursoById(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + id));
    }

    private CursoResponse toResponse(Curso curso) {
        return new CursoResponse(
                curso.getId(),
                curso.getNombre(),
                curso.getNivel(),
                curso.getGradoAnio(),
                curso.getDivision(),
                curso.getCapacidad(),
                curso.getCreatedAt(),
                curso.getUpdatedAt()
        );
    }
}
