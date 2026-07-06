package com.franco.school_management_system.entity;

import com.franco.school_management_system.entity.enums.NivelEducativo;
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
@Table(name = "cursos")
public class Curso extends BaseEntity {

    @Column(nullable = false, length = 30)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NivelEducativo nivel;

    @Column(name = "grado_anio", nullable = false)
    private Integer gradoAnio;

    @Column(nullable = false, length = 5)
    private String division;

    private Integer capacidad;

    @OneToMany(mappedBy = "curso")
    private Set<Inscripcion> inscripciones = new HashSet<>();

    @OneToMany(mappedBy = "curso")
    private Set<CursoMateria> materias = new HashSet<>();
}
