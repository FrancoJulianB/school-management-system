package com.franco.school_management_system.controller;

import com.franco.school_management_system.dto.DocenteRequest;
import com.franco.school_management_system.dto.DocenteResponse;
import com.franco.school_management_system.service.interfaces.DocenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docentes")
@RequiredArgsConstructor
public class DocenteController {

    private final DocenteService docenteService;

    @GetMapping
    public List<DocenteResponse> findAll() {
        return docenteService.findAll();
    }

    @GetMapping("/{id}")
    public DocenteResponse findById(@PathVariable Long id) {
        return docenteService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DocenteResponse create(@Valid @RequestBody DocenteRequest request) {
        return docenteService.create(request);
    }

    @PutMapping("/{id}")
    public DocenteResponse update(@PathVariable Long id, @Valid @RequestBody DocenteRequest request) {
        return docenteService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        docenteService.delete(id);
    }
}
