package com.franco.school_management_system;

import com.franco.school_management_system.dto.MateriaRequest;
import com.franco.school_management_system.dto.MateriaResponse;
import com.franco.school_management_system.entity.Materia;
import com.franco.school_management_system.exception.ResourceNotFoundException;
import com.franco.school_management_system.repository.MateriaRepository;
import com.franco.school_management_system.service.impl.MateriaServiceImpl;
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
class MateriaServiceImplTest {

    @Mock
    private MateriaRepository materiaRepository;

    @InjectMocks
    private MateriaServiceImpl materiaService;

    @Test
    void createShouldSaveMateria() {
        MateriaRequest request = new MateriaRequest(
                "Matemática",
                "Materia de matemática del ciclo básico."
        );

        Materia saved = new Materia();
        saved.setId(1L);
        saved.setNombre("Matemática");
        saved.setDescripcion("Materia de matemática del ciclo básico.");

        when(materiaRepository.save(any(Materia.class))).thenReturn(saved);

        MateriaResponse response = materiaService.create(request);

        assertEquals(1L, response.id());
        assertEquals("Matemática", response.nombre());
        assertEquals("Materia de matemática del ciclo básico.", response.descripcion());

        verify(materiaRepository).save(any(Materia.class));
    }

    @Test
    void updateShouldModifyMateria() {
        Materia existing = new Materia();
        existing.setId(1L);
        existing.setNombre("Matemática");

        MateriaRequest request = new MateriaRequest(
                "Lengua",
                "Materia de lengua y literatura."
        );

        when(materiaRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(materiaRepository.save(existing)).thenReturn(existing);

        MateriaResponse response = materiaService.update(1L, request);

        assertEquals("Lengua", response.nombre());
        assertEquals("Materia de lengua y literatura.", response.descripcion());

        verify(materiaRepository).save(existing);
    }

    @Test
    void findByIdShouldThrowWhenMateriaDoesNotExist() {
        when(materiaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> materiaService.findById(99L));
    }
}