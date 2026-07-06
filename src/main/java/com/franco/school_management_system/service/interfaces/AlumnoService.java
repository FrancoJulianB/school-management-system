package com.franco.school_management_system.service.interfaces;

import com.franco.school_management_system.dto.AlumnoRequest;
import com.franco.school_management_system.dto.AlumnoResponse;

import java.util.List;

public interface AlumnoService {

    List<AlumnoResponse> findAll();

    AlumnoResponse findById(Long id);

    AlumnoResponse create(AlumnoRequest request);

    AlumnoResponse update(Long id, AlumnoRequest request);

    void delete(Long id);
}