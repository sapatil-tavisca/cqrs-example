package com.affinion.customereventsource.dtos;

import lombok.Data;

@Data
public class CreateCustomerRequest {
    private String name;

    private String city;

    private String state;

    private String country;

    private String zipCode;
}
