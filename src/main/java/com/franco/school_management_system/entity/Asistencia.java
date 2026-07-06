package com.franco.school_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "asistencias",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"inscripcion_id", "fecha"})
        }
)
public class Asistencia extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inscripcion_id", nullable = false)
    private Inscripcion inscripcion;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private Boolean presente;

    @Column(length = 255)
    private String observaciones;
}
