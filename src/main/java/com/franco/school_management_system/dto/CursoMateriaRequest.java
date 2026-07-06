package com.franco.school_management_system.dto;

import jakarta.validation.constraints.NotNull;

public record CursoMateriaRequest(
        @NotNull Long cursoId,
        @NotNull Long materiaId,
        @NotNull Long docenteId
) {
}
