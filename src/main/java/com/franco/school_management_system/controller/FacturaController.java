package com.franco.school_management_system.controller;

import com.franco.school_management_system.dto.FacturaRequest;
import com.franco.school_management_system.dto.FacturaResponse;
import com.franco.school_management_system.dto.PagoRequest;
import com.franco.school_management_system.service.interfaces.FacturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService facturaService;

    @GetMapping
    public List<FacturaResponse> findAll() {
        return facturaService.findAll();
    }

    @GetMapping("/{id}")
    public FacturaResponse findById(@PathVariable Long id) {
        return facturaService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FacturaResponse create(@Valid @RequestBody FacturaRequest request) {
        return facturaService.create(request);
    }

    @PutMapping("/{id}")
    public FacturaResponse update(@PathVariable Long id, @Valid @RequestBody FacturaRequest request) {
        return facturaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        facturaService.delete(id);
    }

    @PostMapping("/{id}/pagar")
    public FacturaResponse pagar(@PathVariable Long id, @Valid @RequestBody PagoRequest request) {
        return facturaService.pagar(id, request);
    }
}
