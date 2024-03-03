package com.swiggy.Order.services;

import com.swiggy.Order.requests.CustomerRequest;
import com.swiggy.Order.entities.Customer;
import com.swiggy.Order.responses.CustomerResponse;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<CustomerResponse> register (CustomerRequest customerRequest);
    void getById (Long id);
}
