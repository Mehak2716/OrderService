package com.swiggy.Order.services.impl;

import com.swiggy.Order.requests.CustomerRequest;
import com.swiggy.Order.mapper.CustomerMapper;
import com.swiggy.Order.repositories.CustomerRepository;
import com.swiggy.Order.responses.CustomerResponse;
import com.swiggy.Order.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.swiggy.Order.entities.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    public ResponseEntity<CustomerResponse> register(CustomerRequest customerRequest){
        Customer customer = CustomerMapper.mapToCustomer(customerRequest);
        customer.encryptPassword();
        Customer savedCustomer = customerRepository.save(customer);
        CustomerResponse response = CustomerMapper.mapToResponse(savedCustomer);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

}
