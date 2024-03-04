package com.swiggy.Order.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.Order.entities.Customer;
import com.swiggy.Order.entities.Location;
import com.swiggy.Order.entities.MenuItem;
import com.swiggy.Order.entities.Restaurant;
import com.swiggy.Order.enums.Status;
import com.swiggy.Order.exceptions.ResourceForbiddenException;
import com.swiggy.Order.exceptions.UsernameAlreadyInUseException;
import com.swiggy.Order.repositories.CustomerRepository;
import com.swiggy.Order.requests.CustomerRequest;
import com.swiggy.Order.requests.OrderItemRequest;
import com.swiggy.Order.requests.OrderRequest;
import com.swiggy.Order.responses.OrderResponse;
import com.swiggy.Order.services.impl.CustomerServiceImpl;
import com.swiggy.Order.services.impl.OrderServiceImpl;
import com.swiggy.Order.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.swiggy.Order.constants.ResponseMessage.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderServiceImpl orderService;

    @MockBean
    private Validator validator;


    @Test
    @WithMockUser("user")
    public void testCreateOrderSuccessfully() throws Exception {

        OrderItemRequest orderItemRequest = new OrderItemRequest(new MenuItem(1L,"name",10,"descriptiom"),20);
        Restaurant restaurant = new Restaurant(1L,"test", new Location(), Status.Open);
        OrderRequest orderRequest = new OrderRequest(restaurant,List.of(orderItemRequest));
        String request = new ObjectMapper().writeValueAsString(orderRequest);
        ResponseEntity<OrderResponse> response = new ResponseEntity<>(new OrderResponse(), HttpStatus.CREATED);

        when(validator.validateOrderRequest("user",1L,orderRequest)).thenReturn(true);
        when(orderService.create(anyLong(), any(OrderRequest.class)))
                .thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/order-management/customers/{customerId}/orders",1L)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(validator,times(1)).validateOrderRequest("user",1L,orderRequest);
        verify(orderService, times(1)).create(anyLong(), any(OrderRequest.class));
    }

    @Test
    @WithMockUser("user")
    public void testOrderCreationWithInvalidOrderRequest() throws Exception {
        OrderRequest invalidOrderRequest = new OrderRequest(null, null);
        String request = new ObjectMapper().writeValueAsString(invalidOrderRequest);

        doThrow(new IllegalArgumentException(INVALID_ORDER_ARGUMENT))
                .when(validator).validateOrderRequest(eq("user"),eq(1L),any(OrderRequest.class));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/order-management/customers/{customerId}/orders", 1L)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(INVALID_ORDER_ARGUMENT));

        verify(validator, times(1)).validateOrderRequest(anyString(), anyLong(), any(OrderRequest.class));
        verify(orderService, never()).create(anyLong(), any(OrderRequest.class));
    }

    @Test
    @WithMockUser("user")
    public void testOrderCreationWithEmptyOrderList() throws Exception {
        OrderRequest invalidOrderRequest = new OrderRequest(new Restaurant(), new ArrayList<OrderItemRequest>());
        String request = new ObjectMapper().writeValueAsString(invalidOrderRequest);

        doThrow(new IllegalArgumentException(EMPTY_MENU_LIST))
                .when(validator).validateOrderRequest(eq("user"),eq(1L),any(OrderRequest.class));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/order-management/customers/{customerId}/orders", 1L)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(EMPTY_MENU_LIST));

        verify(validator, times(1)).validateOrderRequest(anyString(), anyLong(), any(OrderRequest.class));
        verify(orderService, never()).create(anyLong(), any(OrderRequest.class));
    }

    @Test
    @WithMockUser("user")
    public void testOrderCreationWithForbiddenUserId() throws Exception {
        OrderRequest invalidOrderRequest = new OrderRequest(new Restaurant(), new ArrayList<OrderItemRequest>());
        String request = new ObjectMapper().writeValueAsString(invalidOrderRequest);

        doThrow(new ResourceForbiddenException(RESOURCE_FORBIDDEN))
                .when(validator).validateOrderRequest(eq("user"),eq(1L),any(OrderRequest.class));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/order-management/customers/{customerId}/orders", 1L)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(RESOURCE_FORBIDDEN));

        verify(validator, times(1)).validateOrderRequest(anyString(), anyLong(), any(OrderRequest.class));
        verify(orderService, never()).create(anyLong(), any(OrderRequest.class));
    }

}
