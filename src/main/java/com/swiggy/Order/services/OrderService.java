package com.swiggy.Order.services;

import com.swiggy.Order.entities.Order;
import com.swiggy.Order.requests.OrderRequest;
import com.swiggy.Order.responses.OrderResponse;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<OrderResponse> create(Long customerId, OrderRequest orderRequest);
}
