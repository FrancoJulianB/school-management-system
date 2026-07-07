package com.franco.school_management_system.repository;

import com.franco.school_management_system.entity.Factura;
import com.franco.school_management_system.entity.enums.EstadoFactura;
import com.franco.school_management_system.entity.enums.TipoFactura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura, Long> {

    Optional<Factura> findByAlumnoIdAndTipoFacturaAndEstado(
            Long alumnoId,
            TipoFactura tipoFactura,
            EstadoFactura estado
    );

    List<Factura> findByAlumnoId(Long alumnoId);
}
