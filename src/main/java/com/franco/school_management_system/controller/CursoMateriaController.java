package com.franco.school_management_system.controller;

import com.franco.school_management_system.dto.CursoMateriaRequest;
import com.franco.school_management_system.dto.CursoMateriaResponse;
import com.franco.school_management_system.service.interfaces.CursoMateriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos-materias")
@RequiredArgsConstructor
public class CursoMateriaController {

    private final CursoMateriaService cursoMateriaService;

    @GetMapping
    public List<CursoMateriaResponse> findAll() {
        return cursoMateriaService.findAll();
    }

    @GetMapping("/{id}")
    public CursoMateriaResponse findById(@PathVariable Long id) {
        return cursoMateriaService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CursoMateriaResponse create(@Valid @RequestBody CursoMateriaRequest request) {
        return cursoMateriaService.create(request);
    }

    @PutMapping("/{id}")
    public CursoMateriaResponse update(@PathVariable Long id, @Valid @RequestBody CursoMateriaRequest request) {
        return cursoMateriaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        cursoMateriaService.delete(id);
    }
}