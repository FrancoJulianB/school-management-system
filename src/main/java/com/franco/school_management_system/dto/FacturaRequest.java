package com.franco.school_management_system.dto;

import com.franco.school_management_system.entity.enums.TipoFactura;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record FacturaRequest(
        @NotNull Long alumnoId,
        @NotNull TipoFactura tipoFactura,
        @NotNull LocalDate fechaVencimiento,
        @Valid List<DetalleFacturaRequest> detalles
) {
}
