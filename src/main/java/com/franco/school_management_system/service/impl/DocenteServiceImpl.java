package com.franco.school_management_system.service.impl;

import com.franco.school_management_system.dto.DocenteRequest;
import com.franco.school_management_system.dto.DocenteResponse;
import com.franco.school_management_system.entity.Docente;
import com.franco.school_management_system.exception.ResourceNotFoundException;
import com.franco.school_management_system.repository.DocenteRepository;
import com.franco.school_management_system.service.interfaces.DocenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocenteServiceImpl implements DocenteService {

    private final DocenteRepository docenteRepository;

    @Override
    public List<DocenteResponse> findAll() {
        return docenteRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public DocenteResponse findById(Long id) {
        return toResponse(getDocenteById(id));
    }

    @Override
    public DocenteResponse create(DocenteRequest request) {
        Docente docente = new Docente();

        docente.setNombre(request.nombre());
        docente.setApellido(request.apellido());
        docente.setDni(request.dni());
        docente.setTelefono(request.telefono());
        docente.setEmail(request.email());

        return toResponse(docenteRepository.save(docente));
    }

    @Override
    public DocenteResponse update(Long id, DocenteRequest request) {
        Docente docente = getDocenteById(id);

        docente.setNombre(request.nombre());
        docente.setApellido(request.apellido());
        docente.setDni(request.dni());
        docente.setTelefono(request.telefono());
        docente.setEmail(request.email());

        return toResponse(docenteRepository.save(docente));
    }

    @Override
    public void delete(Long id) {
        Docente docente = getDocenteById(id);
        docenteRepository.delete(docente);
    }

    private Docente getDocenteById(Long id) {
        return docenteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado con id: " + id));
    }

    private DocenteResponse toResponse(Docente docente) {
        return new DocenteResponse(
                docente.getId(),
                docente.getNombre(),
                docente.getApellido(),
                docente.getDni(),
                docente.getTelefono(),
                docente.getEmail(),
                docente.getCreatedAt(),
                docente.getUpdatedAt()
        );
    }
}
