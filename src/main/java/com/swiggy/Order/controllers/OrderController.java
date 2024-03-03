package com.swiggy.Order.controllers;

import com.swiggy.Order.entities.MenuItem;
import com.swiggy.Order.entities.Order;
import com.swiggy.Order.requests.OrderRequest;
import com.swiggy.Order.responses.OrderResponse;
import com.swiggy.Order.services.OrderService;
import com.swiggy.Order.services.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order-management/customers")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PreAuthorize("@validator.validateOrderRequest(authentication.name,#customerId,#orderRequest)")
    @PostMapping("/{customerId}/orders")
    public ResponseEntity<OrderResponse> create(@PathVariable Long customerId, @RequestBody OrderRequest orderRequest){
        return orderService.create(customerId,orderRequest);
    }
}
