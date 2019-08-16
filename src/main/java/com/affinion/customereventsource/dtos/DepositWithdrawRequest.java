package com.affinion.customereventsource.dtos;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class DepositWithdrawRequest {
    @NotNull
    @Min(1)
    private BigDecimal amount;
}
