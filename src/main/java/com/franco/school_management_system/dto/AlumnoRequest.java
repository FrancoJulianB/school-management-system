package com.franco.school_management_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AlumnoRequest(
        @NotBlank @Size(max = 50)
        String nombre,

        @NotBlank @Size(max = 50)
        String apellido,

        @NotBlank @Size(max = 20)
        String dni,

        LocalDate fechaNacimiento,

        @Size(max = 30)
        String telefono,

        @Email @Size(max = 100)
        String email,

        Boolean activo
) {
}