package com.swiggy.Order.security;

import com.swiggy.Order.entities.Customer;
import com.swiggy.Order.exceptions.UserNotFoundException;
import com.swiggy.Order.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.swiggy.Order.constants.literals.ResponseMessage.USER_NOT_FOUND;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {

        Customer user = customerRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        return new CustomUserDetails(user);
    }


}