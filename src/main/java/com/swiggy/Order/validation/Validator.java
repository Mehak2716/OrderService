package com.swiggy.Order.validation;

import com.swiggy.Order.entities.Customer;
import com.swiggy.Order.exceptions.UsernameAlreadyInUseException;
import com.swiggy.Order.repositories.CustomerRepository;
import com.swiggy.Order.requests.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.swiggy.Order.constants.ResponseMessage.INVALID_REGISTRATION_ARGUMENT;
import static com.swiggy.Order.constants.ResponseMessage.USERNAME_ALREADY_IN_USE;

@Component("validator")
public class Validator {

    @Autowired
    CustomerRepository customerRepository;

    public boolean validateRegistrationRequest(CustomerRequest request){
        if(request.getUsername()==null || request.getPassword()==null || request.getLocation()==null){
            throw new IllegalArgumentException(INVALID_REGISTRATION_ARGUMENT);
        }

        if(!customerRepository.findByUsername(request.getUsername()).isEmpty()){
            throw new UsernameAlreadyInUseException(USERNAME_ALREADY_IN_USE);
        }
        return true;
    }
}
