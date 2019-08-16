package com.affinion.customereventsource.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @Builder.Default
    private BigDecimal availableCredit = new BigDecimal("0.0");

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.NEW;

}
