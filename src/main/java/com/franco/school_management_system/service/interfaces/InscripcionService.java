package com.franco.school_management_system.service.interfaces;

import com.franco.school_management_system.dto.InscripcionRequest;
import com.franco.school_management_system.dto.InscripcionResponse;

import java.util.List;

public interface InscripcionService {

    List<InscripcionResponse> findAll();

    InscripcionResponse findById(Long id);

    InscripcionResponse create(InscripcionRequest request);

    InscripcionResponse update(Long id, InscripcionRequest request);

    void delete(Long id);
}