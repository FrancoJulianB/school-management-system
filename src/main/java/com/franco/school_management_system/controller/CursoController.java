package com.franco.school_management_system.controller;

import com.franco.school_management_system.dto.CursoRequest;
import com.franco.school_management_system.dto.CursoResponse;
import com.franco.school_management_system.service.interfaces.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    public List<CursoResponse> findAll() {
        return cursoService.findAll();
    }

    @GetMapping("/{id}")
    public CursoResponse findById(@PathVariable Long id) {
        return cursoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CursoResponse create(@Valid @RequestBody CursoRequest request) {
        return cursoService.create(request);
    }

    @PutMapping("/{id}")
    public CursoResponse update(@PathVariable Long id, @Valid @RequestBody CursoRequest request) {
        return cursoService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        cursoService.delete(id);
    }
}
