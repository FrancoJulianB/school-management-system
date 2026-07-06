package com.franco.school_management_system.dto;

import java.time.LocalDateTime;

public record CursoMateriaResponse(
        Long id,
        Long cursoId,
        String cursoNombre,
        Long materiaId,
        String materiaNombre,
        Long docenteId,
        String docenteNombre,
        String docenteApellido,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
