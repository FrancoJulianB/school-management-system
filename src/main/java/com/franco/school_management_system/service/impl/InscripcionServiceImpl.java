package com.franco.school_management_system.service.impl;

import com.franco.school_management_system.dto.InscripcionRequest;
import com.franco.school_management_system.dto.InscripcionResponse;
import com.franco.school_management_system.entity.*;
import com.franco.school_management_system.entity.enums.EstadoFactura;
import com.franco.school_management_system.entity.enums.EstadoInscripcion;
import com.franco.school_management_system.entity.enums.TipoFactura;
import com.franco.school_management_system.exception.BusinessException;
import com.franco.school_management_system.exception.ResourceNotFoundException;
import com.franco.school_management_system.repository.*;
import com.franco.school_management_system.service.interfaces.InscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final AlumnoRepository alumnoRepository;
    private final CursoRepository cursoRepository;
    private final CicloLectivoRepository cicloLectivoRepository;
    private final FacturaRepository facturaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponse> findAll() {
        return inscripcionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InscripcionResponse findById(Long id) {
        return toResponse(getInscripcionById(id));
    }

    @Override
    @Transactional
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

        boolean matriculaPagada = facturaRepository
                .findByAlumnoIdAndTipoFacturaAndEstado(
                        alumno.getId(),
                        TipoFactura.MATRICULA,
                        EstadoFactura.PAGADA
                )
                .isPresent();

        if (matriculaPagada) {
            inscripcion.setEstado(EstadoInscripcion.ACTIVA);
        } else {
            inscripcion.setEstado(EstadoInscripcion.CONDICIONAL);
            crearFacturaMatriculaSiNoExiste(alumno);
        }

        return toResponse(inscripcionRepository.save(inscripcion));
    }

    @Override
    @Transactional
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
    @Transactional
    public void delete(Long id) {
        Inscripcion inscripcion = getInscripcionById(id);
        inscripcion.setEstado(EstadoInscripcion.BAJA);
        inscripcionRepository.save(inscripcion);
    }

    private void crearFacturaMatriculaSiNoExiste(Alumno alumno) {
        boolean existeFacturaPendiente = facturaRepository
                .findByAlumnoIdAndTipoFacturaAndEstado(
                        alumno.getId(),
                        TipoFactura.MATRICULA,
                        EstadoFactura.PENDIENTE
                )
                .isPresent();

        if (existeFacturaPendiente) {
            return;
        }

        Factura factura = new Factura();
        factura.setAlumno(alumno);
        factura.setTipoFactura(TipoFactura.MATRICULA);
        factura.setEstado(EstadoFactura.PENDIENTE);
        factura.setFechaEmision(LocalDate.now());
        factura.setFechaVencimiento(LocalDate.now().plusDays(15));

        DetalleFactura detalle = new DetalleFactura();
        detalle.setFactura(factura);
        detalle.setConcepto("Matrícula anual");
        detalle.setMonto(new BigDecimal("350.00"));

        factura.getDetalles().add(detalle);
        factura.setTotal(new BigDecimal("350.00"));

        facturaRepository.save(factura);
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