package com.affinion.customereventsource.converter;

import com.affinion.customereventsource.domain.Address;
import com.affinion.customereventsource.domain.Customer;
import com.affinion.customereventsource.dtos.CreateCustomerRequest;
import org.springframework.core.convert.converter.Converter;

public class CreateCustomerRequestToCustomerConverter implements Converter<CreateCustomerRequest, Customer> {
    @Override
    public Customer convert(CreateCustomerRequest source) {
        if (source == null) {
            return null;
        }
        return Customer.builder()
                .name(source.getName())
                .address(
                        Address.builder()
                                .city(source.getCity())
                                .state(source.getState())
                                .country(source.getCountry())
                                .zipCode(source.getZipCode())
                                .build()
                )
                .build();
    }
}
