package com.franco.school_management_system.service.interfaces;

import com.franco.school_management_system.dto.MateriaRequest;
import com.franco.school_management_system.dto.MateriaResponse;

import java.util.List;

public interface MateriaService {

    List<MateriaResponse> findAll();

    MateriaResponse findById(Long id);

    MateriaResponse create(MateriaRequest request);

    MateriaResponse update(Long id, MateriaRequest request);

    void delete(Long id);
}
