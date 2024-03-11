package com.swiggy.Order.services.impl;

import com.swiggy.Order.entities.Customer;
import com.swiggy.Order.entities.Order;
import com.swiggy.Order.mapper.OrderMapper;
import com.swiggy.Order.repositories.CustomerRepository;
import com.swiggy.Order.repositories.OrderRepository;
import com.swiggy.Order.dto.requests.OrderRequest;
import com.swiggy.Order.dto.responses.OrderResponse;
import com.swiggy.Order.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerRepository customerRepository;
    public ResponseEntity<OrderResponse> create(Long customerId, OrderRequest orderRequest) {
         Customer customer = customerRepository.findById(customerId).orElseThrow();
         Order order = OrderMapper.mapToOrder(customer,orderRequest);
         Order createdOrder = orderRepository.save(order);
         createdOrder.initiateDelivery(orderRequest.getRestaurant());
         OrderResponse response = OrderMapper.mapToResponse(createdOrder);
         return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
