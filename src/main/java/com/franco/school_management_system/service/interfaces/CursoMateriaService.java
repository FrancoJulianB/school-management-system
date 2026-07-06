package com.franco.school_management_system.service.interfaces;

import com.franco.school_management_system.dto.CursoMateriaRequest;
import com.franco.school_management_system.dto.CursoMateriaResponse;

import java.util.List;

public interface CursoMateriaService {

    List<CursoMateriaResponse> findAll();

    CursoMateriaResponse findById(Long id);

    CursoMateriaResponse create(CursoMateriaRequest request);

    CursoMateriaResponse update(Long id, CursoMateriaRequest request);

    void delete(Long id);
}
