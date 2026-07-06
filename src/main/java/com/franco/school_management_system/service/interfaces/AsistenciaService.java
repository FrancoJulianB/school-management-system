package com.franco.school_management_system.service.interfaces;

import com.franco.school_management_system.dto.AsistenciaRequest;
import com.franco.school_management_system.dto.AsistenciaResponse;

import java.util.List;

public interface AsistenciaService {

    List<AsistenciaResponse> findAll();

    AsistenciaResponse findById(Long id);

    AsistenciaResponse create(AsistenciaRequest request);

    AsistenciaResponse update(Long id, AsistenciaRequest request);

    void delete(Long id);
}
