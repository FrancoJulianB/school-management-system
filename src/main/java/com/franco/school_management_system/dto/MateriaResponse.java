package com.franco.school_management_system.dto;

import java.time.LocalDateTime;

public record MateriaResponse(
        Long id,
        String nombre,
        String descripcion,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
