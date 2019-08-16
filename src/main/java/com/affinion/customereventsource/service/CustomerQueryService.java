package com.affinion.customereventsource.service;

import com.affinion.customereventsource.domain.Customer;
import com.affinion.customereventsource.domain.Status;
import com.affinion.customereventsource.dtos.CustomerResponse;
import com.affinion.customereventsource.exception.CustomerNotFoundException;
import com.affinion.customereventsource.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerQueryService {

    private final CustomerRepository customerRepository;

    private final ConversionService conversionService;

    public CustomerResponse getCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));
        return conversionService.convert(customer, CustomerResponse.class);
    }

    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .filter(customer -> customer.getStatus() == Status.ACTIVE)
                .map(customer -> conversionService.convert(customer, CustomerResponse.class))
                .collect(Collectors.toList());
    }
}
