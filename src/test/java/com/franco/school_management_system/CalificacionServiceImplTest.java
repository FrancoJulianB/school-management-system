package com.franco.school_management_system;

import com.franco.school_management_system.dto.CalificacionRequest;
import com.franco.school_management_system.dto.CalificacionResponse;
import com.franco.school_management_system.entity.*;
import com.franco.school_management_system.repository.CalificacionRepository;
import com.franco.school_management_system.repository.CursoMateriaRepository;
import com.franco.school_management_system.repository.InscripcionRepository;
import com.franco.school_management_system.service.impl.CalificacionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalificacionServiceImplTest {

    @Mock
    private CalificacionRepository calificacionRepository;

    @Mock
    private InscripcionRepository inscripcionRepository;

    @Mock
    private CursoMateriaRepository cursoMateriaRepository;

    @InjectMocks
    private CalificacionServiceImpl calificacionService;

    @Test
    void createShouldSaveCalificacion() {
        Alumno alumno = new Alumno();
        alumno.setId(1L);
        alumno.setNombre("Juan");
        alumno.setApellido("Pérez");

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId(1L);
        inscripcion.setAlumno(alumno);

        Materia materia = new Materia();
        materia.setNombre("Matemática");

        Docente docente = new Docente();
        docente.setNombre("Carlos");
        docente.setApellido("Rodríguez");

        CursoMateria cursoMateria = new CursoMateria();
        cursoMateria.setId(1L);
        cursoMateria.setMateria(materia);
        cursoMateria.setDocente(docente);

        Calificacion saved = new Calificacion();
        saved.setId(1L);
        saved.setInscripcion(inscripcion);
        saved.setCursoMateria(cursoMateria);
        saved.setNota(new BigDecimal("8.50"));
        saved.setTipoEvaluacion("Parcial");
        saved.setFecha(LocalDate.of(2026, 7, 7));
        saved.setObservaciones("Buen desempeño");

        when(inscripcionRepository.findById(1L)).thenReturn(Optional.of(inscripcion));
        when(cursoMateriaRepository.findById(1L)).thenReturn(Optional.of(cursoMateria));
        when(calificacionRepository.save(any(Calificacion.class))).thenReturn(saved);

        CalificacionRequest request = new CalificacionRequest(
                1L,
                1L,
                new BigDecimal("8.50"),
                "Parcial",
                LocalDate.of(2026, 7, 7),
                "Buen desempeño"
        );

        CalificacionResponse response = calificacionService.create(request);

        assertEquals("Juan", response.alumnoNombre());
        assertEquals("Pérez", response.alumnoApellido());
        assertEquals("Matemática", response.materia());
        assertEquals(new BigDecimal("8.50"), response.nota());
        verify(calificacionRepository).save(any(Calificacion.class));
    }

    @Test
    void deleteShouldDeleteCalificacion() {
        Calificacion calificacion = new Calificacion();
        calificacion.setId(1L);

        when(calificacionRepository.findById(1L)).thenReturn(Optional.of(calificacion));

        calificacionService.delete(1L);

        verify(calificacionRepository).delete(calificacion);
    }
}
