package com.franco.school_management_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DocenteRequest(
        @NotBlank @Size(max = 50)
        String nombre,

        @NotBlank @Size(max = 50)
        String apellido,

        @NotBlank @Size(max = 20)
        String dni,

        @Size(max = 30)
        String telefono,

        @Email @Size(max = 100)
        String email
) {
}
