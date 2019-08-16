package com.affinion.customereventsource.dtos;

import com.affinion.customereventsource.domain.Address;
import com.affinion.customereventsource.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    private Long id;

    private String name;

    private Address address;

    private Status status;

    private BigDecimal availableCredit;

}
