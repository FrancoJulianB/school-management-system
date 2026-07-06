package com.franco.school_management_system.dto;

import com.franco.school_management_system.entity.enums.EstadoInscripcion;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record InscripcionRequest(
        @NotNull
        Long alumnoId,

        @NotNull
        Long cursoId,

        @NotNull
        Long cicloLectivoId,

        LocalDate fechaInscripcion,

        EstadoInscripcion estado
) {
}