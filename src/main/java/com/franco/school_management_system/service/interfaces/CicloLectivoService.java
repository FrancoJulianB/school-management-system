package com.franco.school_management_system.service.interfaces;

import com.franco.school_management_system.dto.CicloLectivoRequest;
import com.franco.school_management_system.dto.CicloLectivoResponse;

import java.util.List;

public interface CicloLectivoService {

    List<CicloLectivoResponse> findAll();

    CicloLectivoResponse findById(Long id);

    CicloLectivoResponse create(CicloLectivoRequest request);

    CicloLectivoResponse update(Long id, CicloLectivoRequest request);

    void delete(Long id);
}
