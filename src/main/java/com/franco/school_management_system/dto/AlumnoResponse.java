package com.franco.school_management_system.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AlumnoResponse(
        Long id,
        String nombre,
        String apellido,
        String dni,
        LocalDate fechaNacimiento,
        String telefono,
        String email,
        Boolean activo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}