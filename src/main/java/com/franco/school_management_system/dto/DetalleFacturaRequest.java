package com.franco.school_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DetalleFacturaRequest(
        @NotBlank String concepto,
        @NotNull BigDecimal monto
) {
}
