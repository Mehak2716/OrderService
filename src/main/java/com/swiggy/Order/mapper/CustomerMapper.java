package com.swiggy.Order.mapper;

import com.swiggy.Order.dto.requests.CustomerRequest;
import com.swiggy.Order.entities.Customer;
import com.swiggy.Order.dto.responses.CustomerResponse;
import com.swiggy.Order.dto.responses.LocationResponse;

public class CustomerMapper {

    public static Customer mapToCustomer(CustomerRequest customerRequest){

        Customer customer = new Customer(
                customerRequest.getUsername(),
                customerRequest.getPassword(),
                customerRequest.getLocation());

        return customer;
    }

   public static CustomerResponse mapToResponse(Customer customer){
        return  new CustomerResponse(
                customer.getId(),
                customer.getUsername(),
                new LocationResponse(
                        customer.getLocation().getXcordinate(),
                        customer.getLocation().getYcordinate()
                )
        );
   }

}
