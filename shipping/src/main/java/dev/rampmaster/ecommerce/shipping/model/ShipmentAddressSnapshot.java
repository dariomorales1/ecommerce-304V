package dev.rampmaster.ecommerce.shipping.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ShipmentAddressSnapshot {

    private String street;
    private String number;
    private String postalCode;
    private String city;
    private String country;

}
