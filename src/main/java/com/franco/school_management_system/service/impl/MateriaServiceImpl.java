package com.franco.school_management_system.service.impl;

import com.franco.school_management_system.dto.MateriaRequest;
import com.franco.school_management_system.dto.MateriaResponse;
import com.franco.school_management_system.entity.Materia;
import com.franco.school_management_system.exception.ResourceNotFoundException;
import com.franco.school_management_system.repository.MateriaRepository;
import com.franco.school_management_system.service.interfaces.MateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MateriaServiceImpl implements MateriaService {

    private final MateriaRepository materiaRepository;

    @Override
    public List<MateriaResponse> findAll() {
        return materiaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public MateriaResponse findById(Long id) {
        return toResponse(getMateriaById(id));
    }

    @Override
    public MateriaResponse create(MateriaRequest request) {
        Materia materia = new Materia();

        materia.setNombre(request.nombre());
        materia.setDescripcion(request.descripcion());

        return toResponse(materiaRepository.save(materia));
    }

    @Override
    public MateriaResponse update(Long id, MateriaRequest request) {
        Materia materia = getMateriaById(id);

        materia.setNombre(request.nombre());
        materia.setDescripcion(request.descripcion());

        return toResponse(materiaRepository.save(materia));
    }

    @Override
    public void delete(Long id) {
        Materia materia = getMateriaById(id);
        materiaRepository.delete(materia);
    }

    private Materia getMateriaById(Long id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con id: " + id));
    }

    private MateriaResponse toResponse(Materia materia) {
        return new MateriaResponse(
                materia.getId(),
                materia.getNombre(),
                materia.getDescripcion(),
                materia.getCreatedAt(),
                materia.getUpdatedAt()
        );
    }
}
