package com.franco.school_management_system.service.impl;

import com.franco.school_management_system.dto.*;
import com.franco.school_management_system.entity.*;
import com.franco.school_management_system.entity.enums.EstadoFactura;
import com.franco.school_management_system.entity.enums.EstadoInscripcion;
import com.franco.school_management_system.entity.enums.TipoFactura;
import com.franco.school_management_system.exception.BusinessException;
import com.franco.school_management_system.exception.ResourceNotFoundException;
import com.franco.school_management_system.repository.AlumnoRepository;
import com.franco.school_management_system.repository.FacturaRepository;
import com.franco.school_management_system.repository.InscripcionRepository;
import com.franco.school_management_system.repository.PagoRepository;
import com.franco.school_management_system.service.interfaces.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository facturaRepository;
    private final AlumnoRepository alumnoRepository;
    private final PagoRepository pagoRepository;
    private final InscripcionRepository inscripcionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<FacturaResponse> findAll() {
        return facturaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FacturaResponse findById(Long id) {
        return toResponse(getFacturaById(id));
    }

    @Override
    @Transactional
    public FacturaResponse create(FacturaRequest request) {
        Alumno alumno = getAlumnoById(request.alumnoId());

        Factura factura = new Factura();
        factura.setAlumno(alumno);
        factura.setTipoFactura(request.tipoFactura());
        factura.setEstado(EstadoFactura.PENDIENTE);
        factura.setFechaEmision(LocalDate.now());
        factura.setFechaVencimiento(request.fechaVencimiento());

        if (request.detalles() != null) {
            request.detalles().forEach(detalleRequest -> {
                DetalleFactura detalle = new DetalleFactura();
                detalle.setFactura(factura);
                detalle.setConcepto(detalleRequest.concepto());
                detalle.setMonto(detalleRequest.monto());
                factura.getDetalles().add(detalle);
            });
        }

        factura.setTotal(calcularTotal(factura));

        return toResponse(facturaRepository.save(factura));
    }

    @Override
    @Transactional
    public FacturaResponse update(Long id, FacturaRequest request) {
        Factura factura = getFacturaById(id);

        if (factura.getEstado() == EstadoFactura.PAGADA) {
            throw new BusinessException("No se puede modificar una factura pagada.");
        }

        Alumno alumno = getAlumnoById(request.alumnoId());

        factura.setAlumno(alumno);
        factura.setTipoFactura(request.tipoFactura());
        factura.setFechaVencimiento(request.fechaVencimiento());

        factura.getDetalles().clear();

        if (request.detalles() != null) {
            request.detalles().forEach(detalleRequest -> {
                DetalleFactura detalle = new DetalleFactura();
                detalle.setFactura(factura);
                detalle.setConcepto(detalleRequest.concepto());
                detalle.setMonto(detalleRequest.monto());
                factura.getDetalles().add(detalle);
            });
        }

        factura.setTotal(calcularTotal(factura));

        return toResponse(facturaRepository.save(factura));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Factura factura = getFacturaById(id);

        if (factura.getEstado() == EstadoFactura.PAGADA) {
            throw new BusinessException("No se puede eliminar una factura pagada.");
        }

        facturaRepository.delete(factura);
    }

    @Override
    @Transactional
    public FacturaResponse pagar(Long id, PagoRequest request) {
        Factura factura = getFacturaById(id);

        if (factura.getEstado() == EstadoFactura.PAGADA) {
            throw new BusinessException("La factura ya se encuentra pagada.");
        }

        Pago pago = new Pago();
        pago.setFactura(factura);
        pago.setFechaPago(LocalDateTime.now());
        pago.setMonto(factura.getTotal());
        pago.setMedioPago(request.medioPago());

        pagoRepository.save(pago);

        factura.setEstado(EstadoFactura.PAGADA);

        if (factura.getTipoFactura() == TipoFactura.MATRICULA) {
            activarInscripcionesCondicionales(factura.getAlumno().getId());
        }

        return toResponse(facturaRepository.save(factura));
    }

    private void activarInscripcionesCondicionales(Long alumnoId) {
        List<Inscripcion> inscripciones = inscripcionRepository.findAll()
                .stream()
                .filter(inscripcion -> inscripcion.getAlumno().getId().equals(alumnoId))
                .filter(inscripcion -> inscripcion.getEstado() == EstadoInscripcion.CONDICIONAL)
                .toList();

        inscripciones.forEach(inscripcion -> {
            inscripcion.setEstado(EstadoInscripcion.ACTIVA);
            inscripcionRepository.save(inscripcion);
        });
    }

    private Alumno getAlumnoById(Long id) {
        return alumnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado con id: " + id));
    }

    private Factura getFacturaById(Long id) {
        return facturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada con id: " + id));
    }

    private BigDecimal calcularTotal(Factura factura) {
        return factura.getDetalles()
                .stream()
                .map(DetalleFactura::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private FacturaResponse toResponse(Factura factura) {
        return new FacturaResponse(
                factura.getId(),
                factura.getAlumno().getId(),
                factura.getAlumno().getNombre(),
                factura.getAlumno().getApellido(),
                factura.getTipoFactura(),
                factura.getEstado(),
                factura.getFechaEmision(),
                factura.getFechaVencimiento(),
                factura.getTotal(),
                factura.getDetalles()
                        .stream()
                        .map(detalle -> new DetalleFacturaResponse(
                                detalle.getId(),
                                detalle.getConcepto(),
                                detalle.getMonto()
                        ))
                        .toList(),
                factura.getCreatedAt(),
                factura.getUpdatedAt()
        );
    }
}
