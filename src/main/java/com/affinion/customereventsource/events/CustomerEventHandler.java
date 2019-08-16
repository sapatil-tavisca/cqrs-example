package com.affinion.customereventsource.events;

import com.affinion.customereventsource.domain.Customer;
import com.affinion.customereventsource.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@EnableBinding(Sink.class)
public class CustomerEventHandler {

    private final CustomerRepository customerRepository;

    @StreamListener(Sink.INPUT)
    public void handleCustomerEvents(Customer customer) {
        customerRepository.save(customer);
    }

}
