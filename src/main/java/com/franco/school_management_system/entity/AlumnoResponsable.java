package com.franco.school_management_system.entity;

import com.franco.school_management_system.entity.enums.Parentesco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "alumnos_responsables",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"alumno_id", "responsable_legal_id"})
        }
)
public class AlumnoResponsable extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "responsable_legal_id", nullable = false)
    private ResponsableLegal responsableLegal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Parentesco parentesco;
}
