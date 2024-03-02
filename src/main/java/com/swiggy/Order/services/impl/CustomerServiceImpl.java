package com.swiggy.Order.services.impl;

import com.swiggy.Order.dto.CustomerDto;
import com.swiggy.Order.mapper.CustomerMapper;
import com.swiggy.Order.repositories.CustomerRepository;
import com.swiggy.Order.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.swiggy.Order.entities.Customer;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    public void register(CustomerDto customerDto){
        Customer customer = CustomerMapper.mapToCustomer(customerDto);
        customerRepository.save(customer);
    }

    public void getById(Long id){
        Optional<Customer> customer = customerRepository.findById(id);

        Customer customer1 = customer.get();
        System.out.println(customer1.getId());
        System.out.println(customer1.getLocation());
    }
}
