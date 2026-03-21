package dev.rampmaster.ecommerce.shipping.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderResponse {

    private Long id;
    private Long userId;
    private BigDecimal total;
    private List<OrderItemResponse> items;
}
