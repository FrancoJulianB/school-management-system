package com.franco.school_management_system.repository;

import com.franco.school_management_system.entity.CursoMateria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CursoMateriaRepository extends JpaRepository<CursoMateria, Long> {

    Optional<CursoMateria> findByCursoIdAndMateriaId(Long cursoId, Long materiaId);
}
