package com.swiggy.Order.services;

import com.swiggy.Order.dto.requests.OrderRequest;
import com.swiggy.Order.dto.responses.OrderResponse;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<OrderResponse> create(Long customerId, OrderRequest orderRequest);
}
