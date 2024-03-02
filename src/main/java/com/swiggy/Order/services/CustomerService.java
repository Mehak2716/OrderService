package com.swiggy.Order.services;

import com.swiggy.Order.dto.CustomerDto;

public interface CustomerService {
    void register (CustomerDto customerDto);
    void getById (Long id);
}
