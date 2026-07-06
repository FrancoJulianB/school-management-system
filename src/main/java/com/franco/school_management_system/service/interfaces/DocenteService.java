package com.franco.school_management_system.service.interfaces;

import com.franco.school_management_system.dto.DocenteRequest;
import com.franco.school_management_system.dto.DocenteResponse;

import java.util.List;

public interface DocenteService {

    List<DocenteResponse> findAll();

    DocenteResponse findById(Long id);

    DocenteResponse create(DocenteRequest request);

    DocenteResponse update(Long id, DocenteRequest request);

    void delete(Long id);
}
