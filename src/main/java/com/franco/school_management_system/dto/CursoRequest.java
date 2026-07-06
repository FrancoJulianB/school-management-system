package com.franco.school_management_system.dto;

import com.franco.school_management_system.entity.enums.NivelEducativo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CursoRequest(
        @NotBlank @Size(max = 30)
        String nombre,

        @NotNull
        NivelEducativo nivel,

        @NotNull @Min(1)
        Integer gradoAnio,

        @NotBlank @Size(max = 5)
        String division,

        @Min(1)
        Integer capacidad
) {
}
