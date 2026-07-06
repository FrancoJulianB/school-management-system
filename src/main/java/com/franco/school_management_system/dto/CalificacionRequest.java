package com.franco.school_management_system.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalificacionRequest(

        @NotNull
        Long inscripcionId,

        @NotNull
        Long cursoMateriaId,

        @NotNull
        @DecimalMin("0.00")
        @DecimalMax("10.00")
        BigDecimal nota,

        @Size(max = 50)
        String tipoEvaluacion,

        LocalDate fecha,

        @Size(max = 255)
        String observaciones
) {
}
