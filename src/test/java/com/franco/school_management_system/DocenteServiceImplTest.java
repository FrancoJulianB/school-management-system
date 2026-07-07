package com.franco.school_management_system;

import com.franco.school_management_system.dto.DocenteRequest;
import com.franco.school_management_system.dto.DocenteResponse;
import com.franco.school_management_system.entity.Docente;
import com.franco.school_management_system.exception.ResourceNotFoundException;
import com.franco.school_management_system.repository.DocenteRepository;
import com.franco.school_management_system.service.impl.DocenteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocenteServiceImplTest {

    @Mock
    private DocenteRepository docenteRepository;

    @InjectMocks
    private DocenteServiceImpl docenteService;

    @Test
    void createShouldSaveDocente() {
        DocenteRequest request = new DocenteRequest(
                "Carlos",
                "Rodríguez",
                "25111222",
                "1133445566",
                "carlos@test.com"
        );

        Docente saved = new Docente();
        saved.setId(1L);
        saved.setNombre("Carlos");
        saved.setApellido("Rodríguez");
        saved.setDni("25111222");
        saved.setTelefono("1133445566");
        saved.setEmail("carlos@test.com");

        when(docenteRepository.save(any(Docente.class))).thenReturn(saved);

        DocenteResponse response = docenteService.create(request);

        assertEquals(1L, response.id());
        assertEquals("Carlos", response.nombre());
        assertEquals("Rodríguez", response.apellido());
        assertEquals("25111222", response.dni());

        verify(docenteRepository).save(any(Docente.class));
    }

    @Test
    void updateShouldModifyDocente() {
        Docente existing = new Docente();
        existing.setId(1L);
        existing.setNombre("Carlos");

        DocenteRequest request = new DocenteRequest(
                "María",
                "Gómez",
                "30111222",
                "1199887766",
                "maria@test.com"
        );

        when(docenteRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(docenteRepository.save(existing)).thenReturn(existing);

        DocenteResponse response = docenteService.update(1L, request);

        assertEquals("María", response.nombre());
        assertEquals("Gómez", response.apellido());
        assertEquals("30111222", response.dni());

        verify(docenteRepository).save(existing);
    }

    @Test
    void findByIdShouldThrowWhenDocenteDoesNotExist() {
        when(docenteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> docenteService.findById(99L));
    }
}
