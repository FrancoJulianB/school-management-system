package com.franco.school_management_system;

import com.franco.school_management_system.dto.AlumnoRequest;
import com.franco.school_management_system.dto.AlumnoResponse;
import com.franco.school_management_system.entity.Alumno;
import com.franco.school_management_system.exception.ResourceNotFoundException;
import com.franco.school_management_system.repository.AlumnoRepository;
import com.franco.school_management_system.service.impl.AlumnoServiceImpl;
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
class AlumnoServiceImplTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    @Test
    void createShouldSaveAlumno() {
        AlumnoRequest request = new AlumnoRequest(
                "Juan", "Pérez", "40111222",
                LocalDate.of(2010, 5, 12),
                "1122334455",
                "juan@test.com",
                true
        );

        Alumno alumno = new Alumno();
        alumno.setId(1L);
        alumno.setNombre("Juan");
        alumno.setApellido("Pérez");
        alumno.setDni("40111222");
        alumno.setFechaNacimiento(LocalDate.of(2010, 5, 12));
        alumno.setTelefono("1122334455");
        alumno.setEmail("juan@test.com");
        alumno.setActivo(true);

        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumno);

        AlumnoResponse response = alumnoService.create(request);

        assertEquals(1L, response.id());
        assertEquals("Juan", response.nombre());
        assertEquals("Pérez", response.apellido());
        assertEquals("40111222", response.dni());
        verify(alumnoRepository).save(any(Alumno.class));
    }

    @Test
    void findByIdShouldThrowWhenAlumnoDoesNotExist() {
        when(alumnoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> alumnoService.findById(99L));
    }

    @Test
    void deleteShouldSetAlumnoAsInactive() {
        Alumno alumno = new Alumno();
        alumno.setId(1L);
        alumno.setActivo(true);

        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));

        alumnoService.delete(1L);

        assertFalse(alumno.getActivo());
        verify(alumnoRepository).save(alumno);
    }
}