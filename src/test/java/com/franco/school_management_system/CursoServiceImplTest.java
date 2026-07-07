package com.franco.school_management_system;

import com.franco.school_management_system.dto.CursoRequest;
import com.franco.school_management_system.dto.CursoResponse;
import com.franco.school_management_system.entity.Curso;
import com.franco.school_management_system.entity.enums.NivelEducativo;
import com.franco.school_management_system.exception.ResourceNotFoundException;
import com.franco.school_management_system.repository.CursoRepository;
import com.franco.school_management_system.service.impl.CursoServiceImpl;
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
class CursoServiceImplTest {

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private CursoServiceImpl cursoService;

    @Test
    void createShouldSaveCurso() {
        CursoRequest request = new CursoRequest(
                "1° A",
                NivelEducativo.SECUNDARIO,
                1,
                "A",
                30
        );

        Curso saved = new Curso();
        saved.setId(1L);
        saved.setNombre("1° A");
        saved.setNivel(NivelEducativo.SECUNDARIO);
        saved.setGradoAnio(1);
        saved.setDivision("A");
        saved.setCapacidad(30);

        when(cursoRepository.save(any(Curso.class))).thenReturn(saved);

        CursoResponse response = cursoService.create(request);

        assertEquals(1L, response.id());
        assertEquals("1° A", response.nombre());
        assertEquals(NivelEducativo.SECUNDARIO, response.nivel());
        assertEquals(1, response.gradoAnio());
        assertEquals("A", response.division());
        assertEquals(30, response.capacidad());

        verify(cursoRepository).save(any(Curso.class));
    }

    @Test
    void updateShouldModifyCurso() {
        Curso existing = new Curso();
        existing.setId(1L);
        existing.setNombre("1° A");

        CursoRequest request = new CursoRequest(
                "2° B",
                NivelEducativo.SECUNDARIO,
                2,
                "B",
                28
        );

        when(cursoRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(cursoRepository.save(existing)).thenReturn(existing);

        CursoResponse response = cursoService.update(1L, request);

        assertEquals("2° B", response.nombre());
        assertEquals(2, response.gradoAnio());
        assertEquals("B", response.division());
        assertEquals(28, response.capacidad());

        verify(cursoRepository).save(existing);
    }

    @Test
    void findByIdShouldThrowWhenCursoDoesNotExist() {
        when(cursoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cursoService.findById(99L));
    }
}