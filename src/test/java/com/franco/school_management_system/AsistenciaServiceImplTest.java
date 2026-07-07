package com.franco.school_management_system;

import com.franco.school_management_system.dto.AsistenciaRequest;
import com.franco.school_management_system.dto.AsistenciaResponse;
import com.franco.school_management_system.entity.Alumno;
import com.franco.school_management_system.entity.Asistencia;
import com.franco.school_management_system.entity.Inscripcion;
import com.franco.school_management_system.exception.BusinessException;
import com.franco.school_management_system.repository.AsistenciaRepository;
import com.franco.school_management_system.repository.InscripcionRepository;
import com.franco.school_management_system.service.impl.AsistenciaServiceImpl;
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
class AsistenciaServiceImplTest {

    @Mock
    private AsistenciaRepository asistenciaRepository;

    @Mock
    private InscripcionRepository inscripcionRepository;

    @InjectMocks
    private AsistenciaServiceImpl asistenciaService;

    @Test
    void createShouldSaveAsistencia() {
        Alumno alumno = new Alumno();
        alumno.setId(1L);
        alumno.setNombre("Juan");
        alumno.setApellido("Pérez");

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId(1L);
        inscripcion.setAlumno(alumno);

        Asistencia saved = new Asistencia();
        saved.setId(1L);
        saved.setInscripcion(inscripcion);
        saved.setFecha(LocalDate.of(2026, 7, 7));
        saved.setPresente(true);
        saved.setObservaciones("Presente");

        when(inscripcionRepository.findById(1L)).thenReturn(Optional.of(inscripcion));
        when(asistenciaRepository.findByInscripcionIdAndFecha(1L, LocalDate.of(2026, 7, 7)))
                .thenReturn(Optional.empty());
        when(asistenciaRepository.save(any(Asistencia.class))).thenReturn(saved);

        AsistenciaRequest request = new AsistenciaRequest(
                1L,
                LocalDate.of(2026, 7, 7),
                true,
                "Presente"
        );

        AsistenciaResponse response = asistenciaService.create(request);

        assertEquals(1L, response.inscripcionId());
        assertEquals("Juan", response.alumnoNombre());
        assertTrue(response.presente());
        verify(asistenciaRepository).save(any(Asistencia.class));
    }

    @Test
    void createShouldThrowWhenAsistenciaAlreadyExistsForSameDate() {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId(1L);

        when(inscripcionRepository.findById(1L)).thenReturn(Optional.of(inscripcion));
        when(asistenciaRepository.findByInscripcionIdAndFecha(1L, LocalDate.of(2026, 7, 7)))
                .thenReturn(Optional.of(new Asistencia()));

        AsistenciaRequest request = new AsistenciaRequest(
                1L,
                LocalDate.of(2026, 7, 7),
                true,
                null
        );

        assertThrows(BusinessException.class, () -> asistenciaService.create(request));
        verify(asistenciaRepository, never()).save(any(Asistencia.class));
    }
}
