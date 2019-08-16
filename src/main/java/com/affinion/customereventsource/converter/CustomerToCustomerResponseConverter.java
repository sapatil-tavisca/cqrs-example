package com.affinion.customereventsource.converter;

import com.affinion.customereventsource.domain.Customer;
import com.affinion.customereventsource.dtos.CustomerResponse;
import org.springframework.core.convert.converter.Converter;

public class CustomerToCustomerResponseConverter implements Converter<Customer, CustomerResponse> {

    @Override
    public CustomerResponse convert(Customer source) {
        if (source == null) {
            return null;
        }
        return CustomerResponse.builder()
                .id(source.getId())
                .name(source.getName())
                .address(source.getAddress())
                .availableCredit(source.getAvailableCredit())
                .status(source.getStatus())
                .build();
    }
}
