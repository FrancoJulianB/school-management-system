package com.franco.school_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "alumnos")
public class Alumno extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false, unique = true, length = 20)
    private String dni;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(length = 30)
    private String telefono;

    @Column(length = 100)
    private String email;

    @Column(nullable = false)
    private Boolean activo = true;

    @OneToMany(mappedBy = "alumno")
    private Set<AlumnoResponsable> responsables = new HashSet<>();

    @OneToMany(mappedBy = "alumno")
    private Set<Inscripcion> inscripciones = new HashSet<>();
}
