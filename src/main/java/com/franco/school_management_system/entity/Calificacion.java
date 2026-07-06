package com.franco.school_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "calificaciones")
public class Calificacion extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inscripcion_id", nullable = false)
    private Inscripcion inscripcion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "curso_materia_id", nullable = false)
    private CursoMateria cursoMateria;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal nota;

    @Column(name = "tipo_evaluacion", length = 50)
    private String tipoEvaluacion;

    private LocalDate fecha;

    @Column(length = 255)
    private String observaciones;
}
