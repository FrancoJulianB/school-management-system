package com.franco.school_management_system.service.impl;

import com.franco.school_management_system.dto.CursoMateriaRequest;
import com.franco.school_management_system.dto.CursoMateriaResponse;
import com.franco.school_management_system.entity.Curso;
import com.franco.school_management_system.entity.CursoMateria;
import com.franco.school_management_system.entity.Docente;
import com.franco.school_management_system.entity.Materia;
import com.franco.school_management_system.exception.BusinessException;
import com.franco.school_management_system.exception.ResourceNotFoundException;
import com.franco.school_management_system.repository.CursoMateriaRepository;
import com.franco.school_management_system.repository.CursoRepository;
import com.franco.school_management_system.repository.DocenteRepository;
import com.franco.school_management_system.repository.MateriaRepository;
import com.franco.school_management_system.service.interfaces.CursoMateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoMateriaServiceImpl implements CursoMateriaService {

    private final CursoMateriaRepository cursoMateriaRepository;
    private final CursoRepository cursoRepository;
    private final MateriaRepository materiaRepository;
    private final DocenteRepository docenteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CursoMateriaResponse> findAll() {
        return cursoMateriaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CursoMateriaResponse findById(Long id) {
        return toResponse(getCursoMateriaById(id));
    }

    @Override
    public CursoMateriaResponse create(CursoMateriaRequest request) {
        Curso curso = getCursoById(request.cursoId());
        Materia materia = getMateriaById(request.materiaId());
        Docente docente = getDocenteById(request.docenteId());

        cursoMateriaRepository.findByCursoIdAndMateriaId(curso.getId(), materia.getId())
                .ifPresent(existing -> {
                    throw new BusinessException("La materia ya se encuentra asignada a este curso.");
                });

        CursoMateria cursoMateria = new CursoMateria();
        cursoMateria.setCurso(curso);
        cursoMateria.setMateria(materia);
        cursoMateria.setDocente(docente);

        return toResponse(cursoMateriaRepository.save(cursoMateria));
    }

    @Override
    public CursoMateriaResponse update(Long id, CursoMateriaRequest request) {
        CursoMateria cursoMateria = getCursoMateriaById(id);

        Curso curso = getCursoById(request.cursoId());
        Materia materia = getMateriaById(request.materiaId());
        Docente docente = getDocenteById(request.docenteId());

        cursoMateriaRepository.findByCursoIdAndMateriaId(curso.getId(), materia.getId())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new BusinessException("La materia ya se encuentra asignada a este curso.");
                });

        cursoMateria.setCurso(curso);
        cursoMateria.setMateria(materia);
        cursoMateria.setDocente(docente);

        return toResponse(cursoMateriaRepository.save(cursoMateria));
    }

    @Override
    public void delete(Long id) {
        CursoMateria cursoMateria = getCursoMateriaById(id);
        cursoMateriaRepository.delete(cursoMateria);
    }

    private CursoMateria getCursoMateriaById(Long id) {
        return cursoMateriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CursoMateria no encontrada con id: " + id));
    }

    private Curso getCursoById(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + id));
    }

    private Materia getMateriaById(Long id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con id: " + id));
    }

    private Docente getDocenteById(Long id) {
        return docenteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado con id: " + id));
    }

    private CursoMateriaResponse toResponse(CursoMateria cursoMateria) {
        return new CursoMateriaResponse(
                cursoMateria.getId(),
                cursoMateria.getCurso().getId(),
                cursoMateria.getCurso().getNombre(),
                cursoMateria.getMateria().getId(),
                cursoMateria.getMateria().getNombre(),
                cursoMateria.getDocente().getId(),
                cursoMateria.getDocente().getNombre(),
                cursoMateria.getDocente().getApellido(),
                cursoMateria.getCreatedAt(),
                cursoMateria.getUpdatedAt()
        );
    }
}