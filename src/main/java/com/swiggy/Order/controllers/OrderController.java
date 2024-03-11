package com.swiggy.Order.controllers;

import com.swiggy.Order.requests.OrderRequest;
import com.swiggy.Order.responses.OrderResponse;
import com.swiggy.Order.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order-management/")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PreAuthorize("@validator.validateOrderRequest(authentication.name,#customerId,#orderRequest)")
    @PostMapping("customers/{customerId}/orders")
    public ResponseEntity<OrderResponse> create(@PathVariable Long customerId, @RequestBody OrderRequest orderRequest){
        return orderService.create(customerId,orderRequest);
    }

}
