package com.swiggy.Order.validation;

import com.swiggy.Order.entities.Customer;
import com.swiggy.Order.entities.Location;
import com.swiggy.Order.exceptions.UsernameAlreadyInUseException;
import com.swiggy.Order.repositories.CustomerRepository;
import com.swiggy.Order.requests.CustomerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.swiggy.Order.constants.ResponseMessage.INVALID_REGISTRATION_ARGUMENT;
import static com.swiggy.Order.constants.ResponseMessage.USERNAME_ALREADY_IN_USE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class ValidatorTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    Validator validator;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testValidateRegistrationRequestForValidRequest() {
        CustomerRequest validRequest = new CustomerRequest("testUser", "testPassword", new Location());

        when(customerRepository.findByUsername("testUser")).thenReturn(Optional.empty()); // Assuming the username is not already in use

        assertTrue(validator.validateRegistrationRequest(validRequest));
    }

    @Test
    public void testValidateRegistrationRequestForNullUsername() {
        CustomerRequest requestWithNullUsername = new CustomerRequest(null, "testPassword", new Location());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateRegistrationRequest(requestWithNullUsername);
        });

        assertTrue(exception.getMessage().equals(INVALID_REGISTRATION_ARGUMENT));
    }

    @Test
    public void testValidateRegistrationRequestForUsernameAlreadyInUse() {
        CustomerRequest requestWithExistingUsername = new CustomerRequest("existingUser", "testPassword", new Location());

        when(customerRepository.findByUsername("existingUser")).thenReturn(Optional.of(new Customer()));

        UsernameAlreadyInUseException exception = assertThrows(UsernameAlreadyInUseException.class, () -> {
            validator.validateRegistrationRequest(requestWithExistingUsername);
        });

        assertTrue(exception.getMessage().equals(USERNAME_ALREADY_IN_USE));
    }


}
