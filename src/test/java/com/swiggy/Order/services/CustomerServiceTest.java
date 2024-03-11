package com.swiggy.Order.services;

import com.swiggy.Order.entities.Customer;
import com.swiggy.Order.entities.Location;
import com.swiggy.Order.repositories.CustomerRepository;
import com.swiggy.Order.dto.requests.CustomerRequest;
import com.swiggy.Order.dto.responses.CustomerResponse;
import com.swiggy.Order.dto.responses.LocationResponse;
import com.swiggy.Order.services.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCustomerRegisteredSuccessfully(){

        CustomerRequest customerRequest = new CustomerRequest("testUser","testPassword",new Location());
        Customer customer = spy(new Customer("testUser", "testPassword",new Location()));
        CustomerResponse customerResponse = new CustomerResponse(null,"testUser",new LocationResponse());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        ResponseEntity<CustomerResponse> response = customerService.register(customerRequest);

        verify(customerRepository,times(1)).save(any(Customer.class));
        assertEquals(response.getBody(),customerResponse);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }
}
