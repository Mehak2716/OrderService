package com.swiggy.Order.services;

import com.swiggy.Order.entities.*;
import com.swiggy.Order.enums.RestaurantStatus;
import com.swiggy.Order.exceptions.NoAvailableDeliveryPartnerException;
import com.swiggy.Order.repositories.CustomerRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testOrderCreatedSuccessfully(){

        OrderItemRequest orderItemRequest = new OrderItemRequest(new MenuItem(1L,"name",10,"descriptiom"),20);
        Restaurant restaurant = new Restaurant(1L,"test", new Location(), RestaurantStatus.Open);
        OrderRequest orderRequest = new OrderRequest(restaurant,List.of(orderItemRequest));
        List<OrderItem> orderItemList = new ArrayList<>(List.of(new OrderItem(1L,2L,10,20)));
        Customer customer = new Customer(1L,"testUser","testPassword",new Location());
        Order order = spy(new Order(customer,restaurant,orderItemList));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        doNothing().when(order).initiateDelivery(restaurant);

        ResponseEntity<OrderResponse> response = orderService.create(1L,orderRequest);

        verify(customerRepository,times(1)).findById(1L);
        verify(orderRepository,times(1)).save(any(Order.class));
        verify(order,times(1)).initiateDelivery(restaurant);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }


    @Test
    public void testOrderCreationWhenNoDeliveryPartnerAvailable(){

        OrderItemRequest orderItemRequest = new OrderItemRequest(new MenuItem(1L,"name",10,"descriptiom"),20);
        Restaurant restaurant = new Restaurant(1L,"test", new Location(), RestaurantStatus.Open);
        OrderRequest orderRequest = new OrderRequest(restaurant,List.of(orderItemRequest));
        List<OrderItem> orderItemList = new ArrayList<>(List.of(new OrderItem(1L,2L,10,20)));
        Customer customer = new Customer(1L,"testUser","testPassword",new Location());
        Order order = spy(new Order(customer,restaurant,orderItemList));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        doThrow(NoAvailableDeliveryPartnerException.class).when(order).initiateDelivery(restaurant);

        assertThrows(NoAvailableDeliveryPartnerException.class,()->{
            ResponseEntity<OrderResponse> response = orderService.create(1L,orderRequest);
        });

        verify(customerRepository,times(1)).findById(1L);
        verify(orderRepository,times(1)).save(any(Order.class));
        verify(order,times(1)).initiateDelivery(restaurant);
    }
}
