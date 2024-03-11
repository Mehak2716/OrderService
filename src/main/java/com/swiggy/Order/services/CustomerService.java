package com.swiggy.Order.services;

import com.swiggy.Order.dto.requests.CustomerRequest;
import com.swiggy.Order.dto.responses.CustomerResponse;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<CustomerResponse> register (CustomerRequest customerRequest);
}
