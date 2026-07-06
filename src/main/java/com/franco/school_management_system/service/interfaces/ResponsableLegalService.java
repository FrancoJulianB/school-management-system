package com.franco.school_management_system.service.interfaces;

import com.franco.school_management_system.dto.ResponsableLegalRequest;
import com.franco.school_management_system.dto.ResponsableLegalResponse;

import java.util.List;

public interface ResponsableLegalService {

    List<ResponsableLegalResponse> findAll();

    ResponsableLegalResponse findById(Long id);

    ResponsableLegalResponse create(ResponsableLegalRequest request);

    ResponsableLegalResponse update(Long id, ResponsableLegalRequest request);

    void delete(Long id);
}
