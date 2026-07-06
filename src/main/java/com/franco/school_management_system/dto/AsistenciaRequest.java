package com.franco.school_management_system.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AsistenciaRequest(
        @NotNull Long inscripcionId,
        @NotNull LocalDate fecha,
        @NotNull Boolean presente,
        @Size(max = 255) String observaciones
) {
}
