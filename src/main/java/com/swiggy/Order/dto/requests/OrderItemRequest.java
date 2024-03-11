package com.swiggy.Order.dto.requests;

import com.swiggy.Order.dto.data.MenuItem;
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
