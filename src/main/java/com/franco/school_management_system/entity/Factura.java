package com.franco.school_management_system.entity;

import com.franco.school_management_system.entity.enums.EstadoFactura;
import com.franco.school_management_system.entity.enums.TipoFactura;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "facturas")
public class Factura extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_factura", nullable = false, length = 30)
    private TipoFactura tipoFactura;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoFactura estado;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DetalleFactura> detalles = new HashSet<>();

    @OneToMany(mappedBy = "factura")
    private Set<Pago> pagos = new HashSet<>();
}