package com.franco.school_management_system.dto;

import com.franco.school_management_system.entity.enums.EstadoCicloLectivo;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CicloLectivoRequest(
        @NotNull
        Integer anio,

        LocalDate fechaInicio,

        LocalDate fechaFin,

        EstadoCicloLectivo estado
) {
}
