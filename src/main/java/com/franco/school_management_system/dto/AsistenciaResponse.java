package com.franco.school_management_system.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AsistenciaResponse(
        Long id,
        Long inscripcionId,
        Long alumnoId,
        String alumnoNombre,
        String alumnoApellido,
        LocalDate fecha,
        Boolean presente,
        String observaciones,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
