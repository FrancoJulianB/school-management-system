package com.franco.school_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "responsables_legales")
public class ResponsableLegal extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false, unique = true, length = 20)
    private String dni;

    @Column(length = 30)
    private String telefono;

    @Column(length = 100)
    private String email;

    @OneToMany(mappedBy = "responsableLegal")
    private Set<AlumnoResponsable> alumnos = new HashSet<>();
}
