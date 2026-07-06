package com.franco.school_management_system.controller;

import com.franco.school_management_system.dto.CalificacionRequest;
import com.franco.school_management_system.dto.CalificacionResponse;
import com.franco.school_management_system.service.interfaces.CalificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calificaciones")
@RequiredArgsConstructor
public class CalificacionController {

    private final CalificacionService calificacionService;

    @GetMapping
    public List<CalificacionResponse> findAll() {
        return calificacionService.findAll();
    }

    @GetMapping("/{id}")
    public CalificacionResponse findById(@PathVariable Long id) {
        return calificacionService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CalificacionResponse create(@Valid @RequestBody CalificacionRequest request) {
        return calificacionService.create(request);
    }

    @PutMapping("/{id}")
    public CalificacionResponse update(@PathVariable Long id,
                                       @Valid @RequestBody CalificacionRequest request) {
        return calificacionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        calificacionService.delete(id);
    }

}
