package com.franco.school_management_system.controller;

import com.franco.school_management_system.dto.CicloLectivoRequest;
import com.franco.school_management_system.dto.CicloLectivoResponse;
import com.franco.school_management_system.service.interfaces.CicloLectivoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ciclos-lectivos")
@RequiredArgsConstructor
public class CicloLectivoController {

    private final CicloLectivoService cicloLectivoService;

    @GetMapping
    public List<CicloLectivoResponse> findAll() {
        return cicloLectivoService.findAll();
    }

    @GetMapping("/{id}")
    public CicloLectivoResponse findById(@PathVariable Long id) {
        return cicloLectivoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CicloLectivoResponse create(@Valid @RequestBody CicloLectivoRequest request) {
        return cicloLectivoService.create(request);
    }

    @PutMapping("/{id}")
    public CicloLectivoResponse update(@PathVariable Long id, @Valid @RequestBody CicloLectivoRequest request) {
        return cicloLectivoService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        cicloLectivoService.delete(id);
    }
}
