package com.swiggy.Order.controllers;


import com.swiggy.Order.requests.CustomerRequest;
import com.swiggy.Order.entities.Customer;
import com.swiggy.Order.responses.CustomerResponse;
import com.swiggy.Order.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order-management/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @PostMapping
    public ResponseEntity<CustomerResponse> register(@RequestBody CustomerRequest customerRequest){
        System.out.println(customerRequest);
        return customerService.register(customerRequest);
    }

    @GetMapping
    public void get(@RequestBody Long id){
        customerService.getById(id);
    }
}
