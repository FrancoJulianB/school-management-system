package com.franco.school_management_system.service.interfaces;

import com.franco.school_management_system.dto.CalificacionRequest;
import com.franco.school_management_system.dto.CalificacionResponse;

import java.util.List;

public interface CalificacionService {

    List<CalificacionResponse> findAll();

    CalificacionResponse findById(Long id);

    CalificacionResponse create(CalificacionRequest request);

    CalificacionResponse update(Long id, CalificacionRequest request);

    void delete(Long id);

}
