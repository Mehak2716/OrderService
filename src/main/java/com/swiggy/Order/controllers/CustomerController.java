package com.swiggy.Order.controllers;


import com.swiggy.Order.dto.requests.CustomerRequest;
import com.swiggy.Order.dto.responses.CustomerResponse;
import com.swiggy.Order.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order-management/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PreAuthorize("@validator.validateRegistrationRequest(#customerRequest)")
    @PostMapping
    public ResponseEntity<CustomerResponse> register(@RequestBody CustomerRequest customerRequest){
        return customerService.register(customerRequest);
    }
}
