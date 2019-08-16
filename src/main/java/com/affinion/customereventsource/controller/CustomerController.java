package com.affinion.customereventsource.controller;

import com.affinion.customereventsource.dtos.CreateCustomerRequest;
import com.affinion.customereventsource.dtos.CustomerResponse;
import com.affinion.customereventsource.dtos.DepositWithdrawRequest;
import com.affinion.customereventsource.service.CustomerCommandService;
import com.affinion.customereventsource.service.CustomerQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerCommandService customerCommandService;

    private final CustomerQueryService customerQueryService;

    @PostMapping
    public void createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        customerCommandService.createCustomer(createCustomerRequest);
    }

    @PutMapping("/{id}/activate")
    public void activateCustomer(@PathVariable Long id) {
        customerCommandService.activateCustomer(id);
    }

    @PutMapping("/{id}/deposit")
    public void depositMoney(@PathVariable Long id, @RequestBody @Valid DepositWithdrawRequest request) {
        customerCommandService.depositAmount(id, request);
    }

    @PutMapping("/{id}/withdraw")
    public void withdrawMoney(@PathVariable Long id, @RequestBody @Valid DepositWithdrawRequest request) {
        customerCommandService.withdrawAmount(id, request);
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomer(@PathVariable Long id) {
        return customerQueryService.getCustomer(id);
    }

    @GetMapping
    List<CustomerResponse> findAllActiveCustomers() {
        return customerQueryService.getAllCustomers();
    }


}
