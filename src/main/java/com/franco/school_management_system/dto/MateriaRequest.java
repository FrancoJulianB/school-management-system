package com.franco.school_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MateriaRequest(
        @NotBlank @Size(max = 60)
        String nombre,

        @Size(max = 255)
        String descripcion
) {
}