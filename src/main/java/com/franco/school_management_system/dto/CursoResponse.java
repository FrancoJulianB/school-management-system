package com.franco.school_management_system.dto;

import com.franco.school_management_system.entity.enums.NivelEducativo;

import java.time.LocalDateTime;

public record CursoResponse(
        Long id,
        String nombre,
        NivelEducativo nivel,
        Integer gradoAnio,
        String division,
        Integer capacidad,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}