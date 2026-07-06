package com.franco.school_management_system.service.interfaces;

import com.franco.school_management_system.dto.CursoRequest;
import com.franco.school_management_system.dto.CursoResponse;

import java.util.List;

public interface CursoService {

    List<CursoResponse> findAll();

    CursoResponse findById(Long id);

    CursoResponse create(CursoRequest request);

    CursoResponse update(Long id, CursoRequest request);

    void delete(Long id);
}
