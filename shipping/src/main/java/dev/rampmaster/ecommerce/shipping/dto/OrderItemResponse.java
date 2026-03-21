package dev.rampmaster.ecommerce.shipping.dto;

import lombok.Data;

@Data
public class OrderItemResponse {
    private Long inventoryId;
    private Integer quantity;
}
