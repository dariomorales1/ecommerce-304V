package dev.rampmaster.ecommerce.shipping.client;

import dev.rampmaster.ecommerce.shipping.dto.OrderResponse;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OrderClient {

    private final WebClient webClient;

    @Value("localhost:8086")
    private String baseUrl;

    public OrderClient(final WebClient webClient) {
        this.webClient = webClient;
    }

    public OrderResponse getOrder(Long orderId) {
        return webClient.get()
                .uri(baseUrl + "/orders/api/orders/{id}", orderId)
                .retrieve()
                .bodyToMono(OrderResponse.class)
                .block();
    }

}
