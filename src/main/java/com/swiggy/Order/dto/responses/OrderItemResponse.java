package com.swiggy.Order.dto.responses;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemResponse {

    private Long menuItemId;

    private int quantity;

    private double totalPrice;
}
