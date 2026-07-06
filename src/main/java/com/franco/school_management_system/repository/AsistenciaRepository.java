package com.franco.school_management_system.repository;

import com.franco.school_management_system.entity.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    Optional<Asistencia> findByInscripcionIdAndFecha(Long inscripcionId, LocalDate fecha);
}
