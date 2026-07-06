package com.franco.school_management_system.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record CalificacionResponse(

        Long id,

        Long inscripcionId,

        Long alumnoId,

        String alumnoNombre,

        String alumnoApellido,

        Long cursoMateriaId,

        String materia,

        String docente,

        BigDecimal nota,

        String tipoEvaluacion,

        LocalDate fecha,

        String observaciones,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
