package com.franco.school_management_system.repository;

import com.franco.school_management_system.entity.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    Optional<Inscripcion> findByAlumnoIdAndCicloLectivoId(Long alumnoId, Long cicloLectivoId);
}