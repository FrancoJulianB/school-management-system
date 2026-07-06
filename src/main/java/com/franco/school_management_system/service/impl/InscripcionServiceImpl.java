package com.franco.school_management_system.service.impl;

import com.franco.school_management_system.dto.InscripcionRequest;
import com.franco.school_management_system.dto.InscripcionResponse;
import com.franco.school_management_system.entity.Alumno;
import com.franco.school_management_system.entity.CicloLectivo;
import com.franco.school_management_system.entity.Curso;
import com.franco.school_management_system.entity.Inscripcion;
import com.franco.school_management_system.entity.enums.EstadoInscripcion;
import com.franco.school_management_system.exception.BusinessException;
import com.franco.school_management_system.exception.ResourceNotFoundException;
import com.franco.school_management_system.repository.AlumnoRepository;
import com.franco.school_management_system.repository.CicloLectivoRepository;
import com.franco.school_management_system.repository.CursoRepository;
import com.franco.school_management_system.repository.InscripcionRepository;
import com.franco.school_management_system.service.interfaces.InscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final AlumnoRepository alumnoRepository;
    private final CursoRepository cursoRepository;
    private final CicloLectivoRepository cicloLectivoRepository;

    @Override
    public List<InscripcionResponse> findAll() {
        return inscripcionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public InscripcionResponse findById(Long id) {
        return toResponse(getInscripcionById(id));
    }

    @Override
    public InscripcionResponse create(InscripcionRequest request) {
        Alumno alumno = getAlumnoById(request.alumnoId());
        Curso curso = getCursoById(request.cursoId());
        CicloLectivo cicloLectivo = getCicloLectivoById(request.cicloLectivoId());

        inscripcionRepository.findByAlumnoIdAndCicloLectivoId(alumno.getId(), cicloLectivo.getId())
                .ifPresent(existing -> {
                    throw new BusinessException("El alumno ya posee una inscripción para ese ciclo lectivo.");
                });

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setAlumno(alumno);
        inscripcion.setCurso(curso);
        inscripcion.setCicloLectivo(cicloLectivo);
        inscripcion.setFechaInscripcion(
                request.fechaInscripcion() != null ? request.fechaInscripcion() : LocalDate.now()
        );
        inscripcion.setEstado(
                request.estado() != null ? request.estado() : EstadoInscripcion.ACTIVA
        );

        return toResponse(inscripcionRepository.save(inscripcion));
    }

    @Override
    public InscripcionResponse update(Long id, InscripcionRequest request) {
        Inscripcion inscripcion = getInscripcionById(id);

        Alumno alumno = getAlumnoById(request.alumnoId());
        Curso curso = getCursoById(request.cursoId());
        CicloLectivo cicloLectivo = getCicloLectivoById(request.cicloLectivoId());

        inscripcionRepository.findByAlumnoIdAndCicloLectivoId(alumno.getId(), cicloLectivo.getId())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new BusinessException("El alumno ya posee una inscripción para ese ciclo lectivo.");
                });

        inscripcion.setAlumno(alumno);
        inscripcion.setCurso(curso);
        inscripcion.setCicloLectivo(cicloLectivo);
        inscripcion.setFechaInscripcion(request.fechaInscripcion());
        inscripcion.setEstado(request.estado());

        return toResponse(inscripcionRepository.save(inscripcion));
    }

    @Override
    public void delete(Long id) {
        Inscripcion inscripcion = getInscripcionById(id);
        inscripcion.setEstado(EstadoInscripcion.BAJA);
        inscripcionRepository.save(inscripcion);
    }

    private Inscripcion getInscripcionById(Long id) {
        return inscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada con id: " + id));
    }

    private Alumno getAlumnoById(Long id) {
        return alumnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado con id: " + id));
    }

    private Curso getCursoById(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + id));
    }

    private CicloLectivo getCicloLectivoById(Long id) {
        return cicloLectivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ciclo lectivo no encontrado con id: " + id));
    }

    private InscripcionResponse toResponse(Inscripcion inscripcion) {
        return new InscripcionResponse(
                inscripcion.getId(),
                inscripcion.getAlumno().getId(),
                inscripcion.getAlumno().getNombre(),
                inscripcion.getAlumno().getApellido(),
                inscripcion.getCurso().getId(),
                inscripcion.getCurso().getNombre(),
                inscripcion.getCicloLectivo().getId(),
                inscripcion.getCicloLectivo().getAnio(),
                inscripcion.getFechaInscripcion(),
                inscripcion.getEstado(),
                inscripcion.getCreatedAt(),
                inscripcion.getUpdatedAt()
        );
    }
}
