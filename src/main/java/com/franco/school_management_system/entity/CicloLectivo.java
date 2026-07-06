package com.franco.school_management_system.entity;

import com.franco.school_management_system.entity.enums.EstadoCicloLectivo;
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
@Table(name = "ciclos_lectivos")
public class CicloLectivo extends BaseEntity {

    @Column(nullable = false, unique = true)
    private Integer anio;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EstadoCicloLectivo estado;

    @OneToMany(mappedBy = "cicloLectivo")
    private Set<Inscripcion> inscripciones = new HashSet<>();
}
