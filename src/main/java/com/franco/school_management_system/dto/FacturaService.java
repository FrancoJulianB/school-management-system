package com.franco.school_management_system.service.interfaces;

import com.franco.school_management_system.dto.FacturaRequest;
import com.franco.school_management_system.dto.FacturaResponse;
import com.franco.school_management_system.dto.PagoRequest;

import java.util.List;

public interface FacturaService {

    List<FacturaResponse> findAll();

    FacturaResponse findById(Long id);

    FacturaResponse create(FacturaRequest request);

    FacturaResponse update(Long id, FacturaRequest request);

    void delete(Long id);

    FacturaResponse pagar(Long id, PagoRequest request);
}
