package com.swiggy.Order.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.Order.entities.Location;
import com.swiggy.Order.exceptions.UsernameAlreadyInUseException;
import com.swiggy.Order.requests.CustomerRequest;
import com.swiggy.Order.responses.CustomerResponse;
import com.swiggy.Order.responses.LocationResponse;
import com.swiggy.Order.services.impl.CustomerServiceImpl;
import com.swiggy.Order.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.swiggy.Order.constants.ResponseMessage.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerServiceImpl customerService;

    @MockBean
    private Validator validator;

    @Test
    public void testCustomerRegisteredSuccessfully() throws Exception {
        CustomerResponse customerResponse = new CustomerResponse(1L,"testUser",new LocationResponse());
        CustomerRequest customerRequest = new CustomerRequest("testUser","abc",new Location());
        String request  = new ObjectMapper().writeValueAsString(customerRequest);
        String expectedJson = new ObjectMapper().writeValueAsString(customerResponse);

        when(validator.validateRegistrationRequest(any(CustomerRequest.class))).thenReturn(true);
        when(customerService.register(any(CustomerRequest.class))).thenReturn(new ResponseEntity<>(customerResponse, HttpStatus.CREATED));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/order-management/customers")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));

        verify(validator,times(1)).validateRegistrationRequest(any(CustomerRequest.class));
        verify(customerService,times(1)).register(any(CustomerRequest.class));
    }

    @Test
    public void testCustomerRegisteredWithInvalidRequest() throws Exception {
        CustomerRequest invalidRequest = new CustomerRequest(null, "abc", new Location());
        String request = new ObjectMapper().writeValueAsString(invalidRequest);


        doThrow(new IllegalArgumentException(INVALID_REGISTRATION_ARGUMENT))
                .when(validator).validateRegistrationRequest(any(CustomerRequest.class));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/order-management/customers")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(INVALID_REGISTRATION_ARGUMENT));

        verify(validator,times(1)).validateRegistrationRequest(any(CustomerRequest.class));
        verify(customerService,never()).register(any(CustomerRequest.class));
    }

    @Test
    public void testRegisterWithUsernameAlreadyInUse() throws Exception {
        CustomerRequest customerRequest = new CustomerRequest("existingUser", "abc", new Location());
        String request = new ObjectMapper().writeValueAsString(customerRequest);

        doThrow(new UsernameAlreadyInUseException(USERNAME_ALREADY_IN_USE))
                .when(validator).validateRegistrationRequest(any(CustomerRequest.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/order-management/customers")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(USERNAME_ALREADY_IN_USE));

        verify(validator,times(1)).validateRegistrationRequest(any(CustomerRequest.class));
        verify(customerService,never()).register(any(CustomerRequest.class));
    }
}
