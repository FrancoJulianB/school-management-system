package com.franco.school_management_system.service.impl;

import com.franco.school_management_system.dto.AlumnoRequest;
import com.franco.school_management_system.dto.AlumnoResponse;
import com.franco.school_management_system.entity.Alumno;
import com.franco.school_management_system.exception.ResourceNotFoundException;
import com.franco.school_management_system.repository.AlumnoRepository;
import com.franco.school_management_system.service.interfaces.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;

    @Override
    public List<AlumnoResponse> findAll() {
        return alumnoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public AlumnoResponse findById(Long id) {
        Alumno alumno = getAlumnoById(id);
        return toResponse(alumno);
    }

    @Override
    public AlumnoResponse create(AlumnoRequest request) {
        Alumno alumno = new Alumno();

        alumno.setNombre(request.nombre());
        alumno.setApellido(request.apellido());
        alumno.setDni(request.dni());
        alumno.setFechaNacimiento(request.fechaNacimiento());
        alumno.setTelefono(request.telefono());
        alumno.setEmail(request.email());
        alumno.setActivo(request.activo() != null ? request.activo() : true);

        Alumno savedAlumno = alumnoRepository.save(alumno);
        return toResponse(savedAlumno);
    }

    @Override
    public AlumnoResponse update(Long id, AlumnoRequest request) {
        Alumno alumno = getAlumnoById(id);

        alumno.setNombre(request.nombre());
        alumno.setApellido(request.apellido());
        alumno.setDni(request.dni());
        alumno.setFechaNacimiento(request.fechaNacimiento());
        alumno.setTelefono(request.telefono());
        alumno.setEmail(request.email());
        alumno.setActivo(request.activo() != null ? request.activo() : alumno.getActivo());

        Alumno updatedAlumno = alumnoRepository.save(alumno);
        return toResponse(updatedAlumno);
    }

    @Override
    public void delete(Long id) {
        Alumno alumno = getAlumnoById(id);
        alumno.setActivo(false);
        alumnoRepository.save(alumno);
    }

    private Alumno getAlumnoById(Long id) {
        return alumnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado con id: " + id));
    }

    private AlumnoResponse toResponse(Alumno alumno) {
        return new AlumnoResponse(
                alumno.getId(),
                alumno.getNombre(),
                alumno.getApellido(),
                alumno.getDni(),
                alumno.getFechaNacimiento(),
                alumno.getTelefono(),
                alumno.getEmail(),
                alumno.getActivo(),
                alumno.getCreatedAt(),
                alumno.getUpdatedAt()
        );
    }
}