package dev.rampmaster.ecommerce.shipping.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long orderId;

    private String trackingCode;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    private boolean paid;

    @Embedded
    private ShipmentAddressSnapshot address;

    private BigDecimal total;

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShipmentItem> items = new ArrayList<>();


}

