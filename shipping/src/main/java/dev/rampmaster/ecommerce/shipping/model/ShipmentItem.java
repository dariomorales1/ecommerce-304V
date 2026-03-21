package dev.rampmaster.ecommerce.shipping.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Shipment_items")
public class ShipmentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

}
