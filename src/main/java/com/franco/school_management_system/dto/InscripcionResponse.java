package com.franco.school_management_system.dto;

import com.franco.school_management_system.entity.enums.EstadoInscripcion;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record InscripcionResponse(
        Long id,
        Long alumnoId,
        String alumnoNombre,
        String alumnoApellido,
        Long cursoId,
        String cursoNombre,
        Long cicloLectivoId,
        Integer anio,
        LocalDate fechaInscripcion,
        EstadoInscripcion estado,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
