package com.franco.school_management_system.controller;

import com.franco.school_management_system.dto.InscripcionRequest;
import com.franco.school_management_system.dto.InscripcionResponse;
import com.franco.school_management_system.service.interfaces.InscripcionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
public class InscripcionController {

    private final InscripcionService inscripcionService;

    @GetMapping
    public List<InscripcionResponse> findAll() {
        return inscripcionService.findAll();
    }

    @GetMapping("/{id}")
    public InscripcionResponse findById(@PathVariable Long id) {
        return inscripcionService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InscripcionResponse create(@Valid @RequestBody InscripcionRequest request) {
        return inscripcionService.create(request);
    }

    @PutMapping("/{id}")
    public InscripcionResponse update(@PathVariable Long id, @Valid @RequestBody InscripcionRequest request) {
        return inscripcionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        inscripcionService.delete(id);
    }
}
