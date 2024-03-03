package com.swiggy.Order.mapper;

import com.swiggy.Order.requests.CustomerRequest;
import com.swiggy.Order.entities.Customer;
import com.swiggy.Order.responses.CustomerResponse;
import com.swiggy.Order.responses.LocationResponse;

public class CustomerMapper {

    public static Customer mapToCustomer(CustomerRequest customerRequest){

        Customer customer = new Customer(
                customerRequest.getName(),
                customerRequest.getPassword(),
                customerRequest.getLocation());

        return customer;
    }

   public static CustomerResponse mapToResponse(Customer customer){
        return  new CustomerResponse(
                customer.getId(),
                customer.getName(),
                new LocationResponse(
                        customer.getLocation().getXcordinate(),
                        customer.getLocation().getYcordinate()
                )
        );
   }

}
