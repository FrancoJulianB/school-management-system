package com.franco.school_management_system.controller;

import com.franco.school_management_system.dto.ResponsableLegalRequest;
import com.franco.school_management_system.dto.ResponsableLegalResponse;
import com.franco.school_management_system.service.interfaces.ResponsableLegalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/responsables-legales")
@RequiredArgsConstructor
public class ResponsableLegalController {

    private final ResponsableLegalService responsableLegalService;

    @GetMapping
    public List<ResponsableLegalResponse> findAll() {
        return responsableLegalService.findAll();
    }

    @GetMapping("/{id}")
    public ResponsableLegalResponse findById(@PathVariable Long id) {
        return responsableLegalService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponsableLegalResponse create(@Valid @RequestBody ResponsableLegalRequest request) {
        return responsableLegalService.create(request);
    }

    @PutMapping("/{id}")
    public ResponsableLegalResponse update(@PathVariable Long id, @Valid @RequestBody ResponsableLegalRequest request) {
        return responsableLegalService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        responsableLegalService.delete(id);
    }
}
