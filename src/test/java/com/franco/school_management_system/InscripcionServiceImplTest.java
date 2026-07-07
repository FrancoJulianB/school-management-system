package com.franco.school_management_system;

import com.franco.school_management_system.dto.InscripcionRequest;
import com.franco.school_management_system.dto.InscripcionResponse;
import com.franco.school_management_system.entity.Alumno;
import com.franco.school_management_system.entity.CicloLectivo;
import com.franco.school_management_system.entity.Curso;
import com.franco.school_management_system.entity.Inscripcion;
import com.franco.school_management_system.entity.enums.EstadoInscripcion;
import com.franco.school_management_system.exception.BusinessException;
import com.franco.school_management_system.repository.AlumnoRepository;
import com.franco.school_management_system.repository.CicloLectivoRepository;
import com.franco.school_management_system.repository.CursoRepository;
import com.franco.school_management_system.repository.InscripcionRepository;
import com.franco.school_management_system.service.impl.InscripcionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InscripcionServiceImplTest {

    @Mock
    private InscripcionRepository inscripcionRepository;

    @Mock
    private AlumnoRepository alumnoRepository;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private CicloLectivoRepository cicloLectivoRepository;

    @InjectMocks
    private InscripcionServiceImpl inscripcionService;

    @Test
    void createShouldSaveInscripcion() {
        Alumno alumno = new Alumno();
        alumno.setId(1L);
        alumno.setNombre("Juan");
        alumno.setApellido("Pérez");

        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("1° A");

        CicloLectivo ciclo = new CicloLectivo();
        ciclo.setId(1L);
        ciclo.setAnio(2026);

        Inscripcion saved = new Inscripcion();
        saved.setId(1L);
        saved.setAlumno(alumno);
        saved.setCurso(curso);
        saved.setCicloLectivo(ciclo);
        saved.setFechaInscripcion(LocalDate.of(2026, 3, 1));
        saved.setEstado(EstadoInscripcion.ACTIVA);

        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        when(cicloLectivoRepository.findById(1L)).thenReturn(Optional.of(ciclo));
        when(inscripcionRepository.findByAlumnoIdAndCicloLectivoId(1L, 1L)).thenReturn(Optional.empty());
        when(inscripcionRepository.save(any(Inscripcion.class))).thenReturn(saved);

        InscripcionRequest request = new InscripcionRequest(
                1L, 1L, 1L,
                LocalDate.of(2026, 3, 1),
                EstadoInscripcion.ACTIVA
        );

        InscripcionResponse response = inscripcionService.create(request);

        assertEquals("Juan", response.alumnoNombre());
        assertEquals("Pérez", response.alumnoApellido());
        assertEquals("1° A", response.cursoNombre());
        assertEquals(2026, response.anio());
        verify(inscripcionRepository).save(any(Inscripcion.class));
    }

    @Test
    void createShouldThrowWhenAlumnoAlreadyHasInscripcionForCicloLectivo() {
        Alumno alumno = new Alumno();
        alumno.setId(1L);

        Curso curso = new Curso();
        curso.setId(1L);

        CicloLectivo ciclo = new CicloLectivo();
        ciclo.setId(1L);

        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        when(cicloLectivoRepository.findById(1L)).thenReturn(Optional.of(ciclo));
        when(inscripcionRepository.findByAlumnoIdAndCicloLectivoId(1L, 1L))
                .thenReturn(Optional.of(new Inscripcion()));

        InscripcionRequest request = new InscripcionRequest(
                1L, 1L, 1L,
                LocalDate.now(),
                EstadoInscripcion.ACTIVA
        );

        assertThrows(BusinessException.class, () -> inscripcionService.create(request));
        verify(inscripcionRepository, never()).save(any(Inscripcion.class));
    }

    @Test
    void deleteShouldSetEstadoBaja() {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId(1L);
        inscripcion.setEstado(EstadoInscripcion.ACTIVA);

        when(inscripcionRepository.findById(1L)).thenReturn(Optional.of(inscripcion));

        inscripcionService.delete(1L);

        assertEquals(EstadoInscripcion.BAJA, inscripcion.getEstado());
        verify(inscripcionRepository).save(inscripcion);
    }
}
