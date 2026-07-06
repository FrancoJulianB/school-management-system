package com.franco.school_management_system.entity;

import com.franco.school_management_system.entity.enums.EstadoInscripcion;
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
@Table(
        name = "inscripciones",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"alumno_id", "ciclo_lectivo_id"})
        }
)
public class Inscripcion extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ciclo_lectivo_id", nullable = false)
    private CicloLectivo cicloLectivo;

    @Column(name = "fecha_inscripcion")
    private LocalDate fechaInscripcion;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EstadoInscripcion estado;

    @OneToMany(mappedBy = "inscripcion")
    private Set<Asistencia> asistencias = new HashSet<>();

    @OneToMany(mappedBy = "inscripcion")
    private Set<Calificacion> calificaciones = new HashSet<>();
}
