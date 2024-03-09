package com.swiggy.Order.validation;

import com.swiggy.Order.entities.Customer;
import com.swiggy.Order.entities.Order;
import com.swiggy.Order.exceptions.OrderNotFoundException;
import com.swiggy.Order.exceptions.ResourceForbiddenException;
import com.swiggy.Order.exceptions.UserNotFoundException;
import com.swiggy.Order.exceptions.UsernameAlreadyInUseException;
import com.swiggy.Order.repositories.CustomerRepository;
import com.swiggy.Order.repositories.OrderRepository;
import com.swiggy.Order.requests.CustomerRequest;
import com.swiggy.Order.requests.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.swiggy.Order.constants.ResponseMessage.*;

@Component("validator")
public class Validator {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    public boolean validateRegistrationRequest(CustomerRequest request){
        if(request.getUsername()==null || request.getPassword()==null || request.getLocation()==null){
            throw new IllegalArgumentException(INVALID_REGISTRATION_ARGUMENT);
        }

        if(!customerRepository.findByUsername(request.getUsername()).isEmpty()){
            throw new UsernameAlreadyInUseException(USERNAME_ALREADY_IN_USE);
        }
        return true;
    }

    public boolean validateOrderRequest(String username, Long customerId, OrderRequest orderRequest){

        if(orderRequest.getRestaurant()==null || orderRequest.getOrderItemList()==null){
            throw new IllegalArgumentException(INVALID_ORDER_ARGUMENT);
        }
        if(orderRequest.getOrderItemList().isEmpty()){
            throw new IllegalArgumentException(EMPTY_MENU_LIST);
        }

        Customer customer = customerRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        if (!Objects.equals(customer.getId(), customerId)){
            throw new ResourceForbiddenException(RESOURCE_FORBIDDEN);
        }
        return true;
    }

}
