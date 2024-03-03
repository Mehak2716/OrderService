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

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    public ResponseEntity<CustomerResponse> register(CustomerRequest customerRequest){
        Customer customer = CustomerMapper.mapToCustomer(customerRequest);
        Customer savedCustomer = customerRepository.save(customer);
        System.out.println(savedCustomer);
        CustomerResponse response = CustomerMapper.mapToResponse(savedCustomer);
        System.out.println(response);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    public void getById(Long id){
        Optional<Customer> customer = customerRepository.findById(id);

        Customer customer1 = customer.get();
        System.out.println(customer1.getId());
        System.out.println(customer1.getLocation());
    }
}
