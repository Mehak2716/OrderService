package com.swiggy.Order.validation;

import com.swiggy.Order.entities.Customer;
import com.swiggy.Order.entities.Location;
import com.swiggy.Order.dto.data.MenuItem;
import com.swiggy.Order.dto.data.Restaurant;
import com.swiggy.Order.exceptions.ResourceForbiddenException;
import com.swiggy.Order.exceptions.UserNotFoundException;
import com.swiggy.Order.exceptions.UsernameAlreadyInUseException;
import com.swiggy.Order.repositories.CustomerRepository;
import com.swiggy.Order.dto.requests.CustomerRequest;
import com.swiggy.Order.dto.requests.OrderItemRequest;
import com.swiggy.Order.dto.requests.OrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.swiggy.Order.constants.literals.ResponseMessage.*;
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

        when(customerRepository.findByUsername("testUser")).thenReturn(Optional.empty());

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

    @Test
    public void testValidateOrderRequestForValidRequest() {
        String username = "testUser";
        Long customerId = 1L;
        MenuItem menuItem = new MenuItem(1L,"testName", 10.0,"testDescription");
        OrderItemRequest orderItemRequest = new OrderItemRequest(menuItem,2);
        OrderRequest validOrderRequest = new OrderRequest(new Restaurant(), List.of(orderItemRequest));

        when(customerRepository.findByUsername(username)).thenReturn(Optional.of(new Customer(1L,"testUser","abc" ,new Location())));

        assertTrue(validator.validateOrderRequest(username, customerId, validOrderRequest));
    }

    @Test
    public void testValidateOrderRequestForNullRestaurant() {
        String username = "testUser";
        Long customerId = 1L;
        MenuItem menuItem = new MenuItem(1L,"testName", 10.0,"testDescription");
        OrderItemRequest orderItemRequest = new OrderItemRequest(menuItem,2);
        OrderRequest orderRequestWithNullRestaurant = new OrderRequest(null, List.of(orderItemRequest));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateOrderRequest(username, customerId, orderRequestWithNullRestaurant);
        });

        assertTrue(exception.getMessage().equals(INVALID_ORDER_ARGUMENT));
    }

    @Test
    public void testValidateOrderRequestForEmptyMenuItemList() {
        String username = "testUser";
        Long customerId = 1L;
        OrderRequest orderRequestWithEmptyMenu = new OrderRequest(new Restaurant(), Collections.emptyList());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateOrderRequest(username, customerId, orderRequestWithEmptyMenu);
        });

        assertTrue(exception.getMessage().equals(EMPTY_MENU_LIST));
    }

    @Test
    public void testValidateOrderRequestForUserNotFound() {
        String username = "nonExistingUser";
        Long customerId = 1L;
        MenuItem menuItem = new MenuItem(1L,"testName", 10.0,"testDescription");
        OrderItemRequest orderItemRequest = new OrderItemRequest(menuItem,2);
        OrderRequest validOrderRequest = new OrderRequest(new Restaurant(), List.of(orderItemRequest));

        when(customerRepository.findByUsername(username)).thenReturn(Optional.empty());
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            validator.validateOrderRequest(username, customerId, validOrderRequest);
        });
        assertTrue(exception.getMessage().equals(USER_NOT_FOUND));
    }

    @Test
    public void testValidateOrderRequestForForbiddenResource() {
        String username = "testUser";
        Long customerId = 2L;
        MenuItem menuItem = new MenuItem(1L,"testName", 10.0,"testDescription");
        OrderItemRequest orderItemRequest = new OrderItemRequest(menuItem,2);
        OrderRequest validOrderRequest = new OrderRequest(new Restaurant(), List.of(orderItemRequest));

        when(customerRepository.findByUsername(username)).thenReturn(Optional.of(new Customer(1L, username, "abc",new Location())));
        ResourceForbiddenException exception = assertThrows(ResourceForbiddenException.class, () -> {
            validator.validateOrderRequest(username, customerId, validOrderRequest);
        });

        assertTrue(exception.getMessage().equals(RESOURCE_FORBIDDEN));
    }
}
