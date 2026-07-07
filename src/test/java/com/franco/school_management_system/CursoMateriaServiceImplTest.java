package com.franco.school_management_system;

import com.franco.school_management_system.dto.CursoMateriaRequest;
import com.franco.school_management_system.dto.CursoMateriaResponse;
import com.franco.school_management_system.entity.Curso;
import com.franco.school_management_system.entity.CursoMateria;
import com.franco.school_management_system.entity.Docente;
import com.franco.school_management_system.entity.Materia;
import com.franco.school_management_system.exception.BusinessException;
import com.franco.school_management_system.repository.CursoMateriaRepository;
import com.franco.school_management_system.repository.CursoRepository;
import com.franco.school_management_system.repository.DocenteRepository;
import com.franco.school_management_system.repository.MateriaRepository;
import com.franco.school_management_system.service.impl.CursoMateriaServiceImpl;
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
class CursoMateriaServiceImplTest {

    @Mock
    private CursoMateriaRepository cursoMateriaRepository;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private MateriaRepository materiaRepository;

    @Mock
    private DocenteRepository docenteRepository;

    @InjectMocks
    private CursoMateriaServiceImpl cursoMateriaService;

    @Test
    void createShouldSaveCursoMateria() {
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("1° A");

        Materia materia = new Materia();
        materia.setId(1L);
        materia.setNombre("Matemática");

        Docente docente = new Docente();
        docente.setId(1L);
        docente.setNombre("Carlos");
        docente.setApellido("Rodríguez");

        CursoMateria saved = new CursoMateria();
        saved.setId(1L);
        saved.setCurso(curso);
        saved.setMateria(materia);
        saved.setDocente(docente);

        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        when(materiaRepository.findById(1L)).thenReturn(Optional.of(materia));
        when(docenteRepository.findById(1L)).thenReturn(Optional.of(docente));
        when(cursoMateriaRepository.findByCursoIdAndMateriaId(1L, 1L)).thenReturn(Optional.empty());
        when(cursoMateriaRepository.save(any(CursoMateria.class))).thenReturn(saved);

        CursoMateriaRequest request = new CursoMateriaRequest(1L, 1L, 1L);

        CursoMateriaResponse response = cursoMateriaService.create(request);

        assertEquals("1° A", response.cursoNombre());
        assertEquals("Matemática", response.materiaNombre());
        assertEquals("Carlos", response.docenteNombre());
        verify(cursoMateriaRepository).save(any(CursoMateria.class));
    }

    @Test
    void createShouldThrowWhenMateriaAlreadyAssignedToCurso() {
        Curso curso = new Curso();
        curso.setId(1L);

        Materia materia = new Materia();
        materia.setId(1L);

        Docente docente = new Docente();
        docente.setId(1L);

        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        when(materiaRepository.findById(1L)).thenReturn(Optional.of(materia));
        when(docenteRepository.findById(1L)).thenReturn(Optional.of(docente));
        when(cursoMateriaRepository.findByCursoIdAndMateriaId(1L, 1L))
                .thenReturn(Optional.of(new CursoMateria()));

        CursoMateriaRequest request = new CursoMateriaRequest(1L, 1L, 1L);

        assertThrows(BusinessException.class, () -> cursoMateriaService.create(request));
        verify(cursoMateriaRepository, never()).save(any(CursoMateria.class));
    }
}
