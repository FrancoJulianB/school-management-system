package com.franco.school_management_system.dto;

import java.math.BigDecimal;

public record DetalleFacturaResponse(
        Long id,
        String concepto,
        BigDecimal monto
) {
}
