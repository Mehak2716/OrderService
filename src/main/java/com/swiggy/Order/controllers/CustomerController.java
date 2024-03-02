package com.swiggy.Order.controllers;


import com.swiggy.Order.dto.CustomerDto;
import com.swiggy.Order.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order-management/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @PostMapping
    public void register(@RequestBody CustomerDto customerDto){

        customerService.register(customerDto);
    }

    @GetMapping
    public void get(@RequestBody Long id){
        customerService.getById(id);
    }
}
