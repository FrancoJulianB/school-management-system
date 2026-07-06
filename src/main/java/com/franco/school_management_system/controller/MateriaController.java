package com.franco.school_management_system.controller;

import com.franco.school_management_system.dto.MateriaRequest;
import com.franco.school_management_system.dto.MateriaResponse;
import com.franco.school_management_system.service.interfaces.MateriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materias")
@RequiredArgsConstructor
public class MateriaController {

    private final MateriaService materiaService;

    @GetMapping
    public List<MateriaResponse> findAll() {
        return materiaService.findAll();
    }

    @GetMapping("/{id}")
    public MateriaResponse findById(@PathVariable Long id) {
        return materiaService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MateriaResponse create(@Valid @RequestBody MateriaRequest request) {
        return materiaService.create(request);
    }

    @PutMapping("/{id}")
    public MateriaResponse update(@PathVariable Long id, @Valid @RequestBody MateriaRequest request) {
        return materiaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        materiaService.delete(id);
    }
}
