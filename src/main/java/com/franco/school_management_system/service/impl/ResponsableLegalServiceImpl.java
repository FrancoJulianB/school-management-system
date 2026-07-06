package com.franco.school_management_system.service.impl;

import com.franco.school_management_system.dto.ResponsableLegalRequest;
import com.franco.school_management_system.dto.ResponsableLegalResponse;
import com.franco.school_management_system.entity.ResponsableLegal;
import com.franco.school_management_system.exception.ResourceNotFoundException;
import com.franco.school_management_system.repository.ResponsableLegalRepository;
import com.franco.school_management_system.service.interfaces.ResponsableLegalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponsableLegalServiceImpl implements ResponsableLegalService {

    private final ResponsableLegalRepository responsableLegalRepository;

    @Override
    public List<ResponsableLegalResponse> findAll() {
        return responsableLegalRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ResponsableLegalResponse findById(Long id) {
        return toResponse(getResponsableLegalById(id));
    }

    @Override
    public ResponsableLegalResponse create(ResponsableLegalRequest request) {
        ResponsableLegal responsableLegal = new ResponsableLegal();

        responsableLegal.setNombre(request.nombre());
        responsableLegal.setApellido(request.apellido());
        responsableLegal.setDni(request.dni());
        responsableLegal.setTelefono(request.telefono());
        responsableLegal.setEmail(request.email());

        return toResponse(responsableLegalRepository.save(responsableLegal));
    }

    @Override
    public ResponsableLegalResponse update(Long id, ResponsableLegalRequest request) {
        ResponsableLegal responsableLegal = getResponsableLegalById(id);

        responsableLegal.setNombre(request.nombre());
        responsableLegal.setApellido(request.apellido());
        responsableLegal.setDni(request.dni());
        responsableLegal.setTelefono(request.telefono());
        responsableLegal.setEmail(request.email());

        return toResponse(responsableLegalRepository.save(responsableLegal));
    }

    @Override
    public void delete(Long id) {
        ResponsableLegal responsableLegal = getResponsableLegalById(id);
        responsableLegalRepository.delete(responsableLegal);
    }

    private ResponsableLegal getResponsableLegalById(Long id) {
        return responsableLegalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Responsable legal no encontrado con id: " + id));
    }

    private ResponsableLegalResponse toResponse(ResponsableLegal responsableLegal) {
        return new ResponsableLegalResponse(
                responsableLegal.getId(),
                responsableLegal.getNombre(),
                responsableLegal.getApellido(),
                responsableLegal.getDni(),
                responsableLegal.getTelefono(),
                responsableLegal.getEmail(),
                responsableLegal.getCreatedAt(),
                responsableLegal.getUpdatedAt()
        );
    }
}
