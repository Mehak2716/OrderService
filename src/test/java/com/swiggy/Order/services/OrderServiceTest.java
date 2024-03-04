package com.swiggy.Order.services;

import com.swiggy.Order.entities.*;
import com.swiggy.Order.enums.Status;
import com.swiggy.Order.repositories.OrderRepository;
import com.swiggy.Order.requests.OrderItemRequest;
import com.swiggy.Order.requests.OrderRequest;
import com.swiggy.Order.responses.OrderResponse;
import com.swiggy.Order.services.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testOrderCreatedSuccessfully(){

        OrderItemRequest orderItemRequest = new OrderItemRequest(new MenuItem(1L,"name",10,"descriptiom"),20);
        Restaurant restaurant = new Restaurant(1L,"test", new Location(), Status.Open);
        OrderRequest orderRequest = new OrderRequest(restaurant,List.of(orderItemRequest));
        List<OrderItem> orderItemList = new ArrayList<>(List.of(new OrderItem(1L,2L,10,20)));
        Order order = new Order(1L,restaurant,orderItemList);

        when(orderRepository.save(any(Order.class))).thenReturn(order);
        ResponseEntity<OrderResponse> response = orderService.create(1L,orderRequest);

        verify(orderRepository,times(1)).save(any(Order.class));
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }
}
