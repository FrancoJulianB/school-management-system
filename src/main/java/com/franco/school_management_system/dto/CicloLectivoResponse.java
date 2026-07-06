package com.franco.school_management_system.dto;

import com.franco.school_management_system.entity.enums.EstadoCicloLectivo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CicloLectivoResponse(
        Long id,
        Integer anio,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        EstadoCicloLectivo estado,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
