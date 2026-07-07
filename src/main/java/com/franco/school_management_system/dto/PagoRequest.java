package com.franco.school_management_system.dto;

import com.franco.school_management_system.entity.enums.MedioPago;
import jakarta.validation.constraints.NotNull;

public record PagoRequest(
        @NotNull MedioPago medioPago
) {
}
