package com.swiggy.Order.services.impl;

import com.swiggy.Order.entities.Order;
import com.swiggy.Order.mapper.OrderMapper;
import com.swiggy.Order.repositories.OrderRepository;
import com.swiggy.Order.requests.OrderRequest;
import com.swiggy.Order.responses.OrderResponse;
import com.swiggy.Order.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    public ResponseEntity<OrderResponse> create(Long customerId, OrderRequest orderRequest) {
         Order order = OrderMapper.mapToOrder(customerId,orderRequest);
         Order createdOrder = orderRepository.save(order);
         OrderResponse response = OrderMapper.mapToResponse(createdOrder);
         return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}
