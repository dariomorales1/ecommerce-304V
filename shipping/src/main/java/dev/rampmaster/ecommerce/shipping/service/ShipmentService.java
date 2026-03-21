package dev.rampmaster.ecommerce.shipping.service;

import dev.rampmaster.ecommerce.shipping.client.OrderClient;
import dev.rampmaster.ecommerce.shipping.dto.CreateShipmentRequest;
import dev.rampmaster.ecommerce.shipping.dto.OrderResponse;
import dev.rampmaster.ecommerce.shipping.model.Shipment;
import dev.rampmaster.ecommerce.shipping.model.ShipmentAddressSnapshot;
import dev.rampmaster.ecommerce.shipping.model.ShipmentItem;
import dev.rampmaster.ecommerce.shipping.model.ShipmentStatus;
import dev.rampmaster.ecommerce.shipping.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentService {

    private final ShipmentRepository repository;
    private final OrderClient orderClient;

    public ShipmentService(ShipmentRepository repository, OrderClient orderClient) {
        this.repository = repository;
        this.orderClient = orderClient;
    }

    public List<Shipment> findAll() {
        return repository.findAll();
    }

    public Optional<Shipment> findById(Long id) {
        return repository.findById(id);
    }

    public Shipment createFromOrder(Long orderId, CreateShipmentRequest request) {

        OrderResponse order = orderClient.getOrder(orderId);

        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        Shipment shipment = new Shipment();
        shipment.setOrderId(order.getId());
        shipment.setUserId(order.getUserId());
        shipment.setStatus(ShipmentStatus.CREATED);
        shipment.setTrackingCode("TRK-" + System.currentTimeMillis());
        shipment.setTotal(order.getTotal());
        shipment.setPaid(true);
        ShipmentAddressSnapshot address = new ShipmentAddressSnapshot();
        address.setStreet(request.getStreet());
        address.setNumber(request.getNumber());
        address.setCity(request.getCity());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        shipment.setAddress(address);
        order.getItems().forEach(item -> {
            ShipmentItem shipmentItem = new ShipmentItem();
            shipmentItem.setProductId(item.getInventoryId());
            shipmentItem.setQuantity(item.getQuantity());
            shipmentItem.setShipment(shipment);
            shipment.getItems().add(shipmentItem);
        });

        return repository.save(shipment);
    }

    public Optional<Shipment> update(Long id, Shipment updated) {
        return repository.findById(id).map(existing -> {

            existing.setUserId(updated.getUserId());
            existing.setOrderId(updated.getOrderId());
            existing.setPaid(updated.isPaid());
            existing.setStatus(updated.getStatus());
            existing.setTotal(updated.getTotal());
            existing.setAddress(updated.getAddress());

            return repository.save(existing);
        });
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}

