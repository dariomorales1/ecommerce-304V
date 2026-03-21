package dev.rampmaster.ecommerce.shipping.dto;

import lombok.Data;

@Data
public class AddressResponse {

    private String street;
    private String number;
    private String city;
    private String postalCode;
    private String country;

}
