package com.franco.school_management_system.dto;

import java.time.LocalDateTime;

public record DocenteResponse(
        Long id,
        String nombre,
        String apellido,
        String dni,
        String telefono,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
