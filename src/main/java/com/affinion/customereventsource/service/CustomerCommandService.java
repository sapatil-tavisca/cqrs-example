package com.affinion.customereventsource.service;

import com.affinion.customereventsource.domain.*;
import com.affinion.customereventsource.dtos.CreateCustomerRequest;
import com.affinion.customereventsource.dtos.DepositWithdrawRequest;
import com.affinion.customereventsource.exception.CustomerNotFoundException;
import com.affinion.customereventsource.repository.CustomerEventRepository;
import com.affinion.customereventsource.repository.CustomerRepository;
import com.google.gson.Gson;
import io.github.teastman.hibernate.annotation.HibernateEventListener;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.PostInsertEvent;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.core.convert.ConversionService;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@EnableBinding(Source.class)
public class CustomerCommandService {

    private final CustomerEventRepository customerEventRepository;

    private final CustomerRepository customerRepository;

    private final ConversionService conversionService;

    private final Source source;

    public void createCustomer(CreateCustomerRequest request) {
        Customer customer = conversionService.convert(request, Customer.class);
        CustomerEvent customerEvent = getCustomerEvent(customer);
        customerEvent.setEventType(EventType.CUSTOMER_EVENT);
        customerEventRepository.save(customerEvent);
    }

    private CustomerEvent getCustomerEvent(Customer customer) {
        return CustomerEvent.builder()
                .customerId(customer.getId())
                .customerPayload(new Gson().toJson(customer))
                .eventDateTime(Instant.now())
                .build();
    }

    @HibernateEventListener
    public void handlePostInsert(CustomerEvent customerEvent, PostInsertEvent event) {
        Customer customer = new Gson().fromJson(customerEvent.getCustomerPayload(), Customer.class);
        source.output().send(
                MessageBuilder.withPayload(customer).build()
        );
    }

    public void activateCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found!"));
        customer.setStatus(Status.ACTIVE);

        CustomerEvent customerEvent = getCustomerEvent(customer);
        customerEvent.setEventType(EventType.CUSTOMER_EVENT);
        customerEventRepository.save(customerEvent);
    }

    public void depositAmount(Long id, DepositWithdrawRequest request) {
        transact(id, request, TransactionStatus.DEPOSIT);
    }

    public void withdrawAmount(Long id, DepositWithdrawRequest request) {
        transact(id, request, TransactionStatus.WITHDRAW);
    }

    private void transact(Long id, DepositWithdrawRequest request, TransactionStatus status) {
        Map<String, BigDecimal> transactions = new HashMap<>();
        transactions.put(String.valueOf(status), request.getAmount());

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found!"));

        if (status == TransactionStatus.DEPOSIT) {
            customer.setAvailableCredit(
                    customer.getAvailableCredit().add(request.getAmount())
            );
        } else {
            if (customer.getAvailableCredit().compareTo(request.getAmount()) > 0) {
                customer.setAvailableCredit(
                        customer.getAvailableCredit().subtract(request.getAmount())
                );
            } else {
                //log error
            }

        }
        CustomerEvent customerEvent = getCustomerEvent(customer);
        customerEvent.setTransactionPayload(new Gson().toJson(transactions));
        customerEvent.setEventType(EventType.TRANSACTION_EVENT);
        customerEventRepository.save(customerEvent);
    }
}
