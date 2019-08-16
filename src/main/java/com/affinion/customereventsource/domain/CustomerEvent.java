package com.affinion.customereventsource.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Builder
public class CustomerEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    private Long customerId;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String customerPayload;

    private String transactionPayload;

    private Instant eventDateTime;
}
