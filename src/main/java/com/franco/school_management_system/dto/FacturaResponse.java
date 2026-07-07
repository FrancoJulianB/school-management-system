package com.franco.school_management_system.dto;

import com.franco.school_management_system.entity.enums.EstadoFactura;
import com.franco.school_management_system.entity.enums.TipoFactura;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record FacturaResponse(
        Long id,
        Long alumnoId,
        String alumnoNombre,
        String alumnoApellido,
        TipoFactura tipoFactura,
        EstadoFactura estado,
        LocalDate fechaEmision,
        LocalDate fechaVencimiento,
        BigDecimal total,
        List<DetalleFacturaResponse> detalles,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
