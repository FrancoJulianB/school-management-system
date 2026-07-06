package com.franco.school_management_system.controller;

import com.franco.school_management_system.dto.AlumnoRequest;
import com.franco.school_management_system.dto.AlumnoResponse;
import com.franco.school_management_system.service.interfaces.AlumnoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoService alumnoService;

    @GetMapping
    public List<AlumnoResponse> findAll() {
        return alumnoService.findAll();
    }

    @GetMapping("/{id}")
    public AlumnoResponse findById(@PathVariable Long id) {
        return alumnoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlumnoResponse create(@Valid @RequestBody AlumnoRequest request) {
        return alumnoService.create(request);
    }

    @PutMapping("/{id}")
    public AlumnoResponse update(@PathVariable Long id, @Valid @RequestBody AlumnoRequest request) {
        return alumnoService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        alumnoService.delete(id);
    }
}