package com.franco.school_management_system.controller;

import com.franco.school_management_system.dto.AsistenciaRequest;
import com.franco.school_management_system.dto.AsistenciaResponse;
import com.franco.school_management_system.service.interfaces.AsistenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
@RequiredArgsConstructor
public class AsistenciaController {

    private final AsistenciaService asistenciaService;

    @GetMapping
    public List<AsistenciaResponse> findAll() {
        return asistenciaService.findAll();
    }

    @GetMapping("/{id}")
    public AsistenciaResponse findById(@PathVariable Long id) {
        return asistenciaService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AsistenciaResponse create(@Valid @RequestBody AsistenciaRequest request) {
        return asistenciaService.create(request);
    }

    @PutMapping("/{id}")
    public AsistenciaResponse update(@PathVariable Long id, @Valid @RequestBody AsistenciaRequest request) {
        return asistenciaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        asistenciaService.delete(id);
    }
}
