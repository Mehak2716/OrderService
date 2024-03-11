package com.swiggy.Order.requests;

import com.swiggy.Order.models.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {

    private MenuItem menuItem;
    private int quantity;
}
