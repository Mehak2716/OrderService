package com.swiggy.Order.requests;

import com.swiggy.Order.models.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private Restaurant restaurant;

    private List<OrderItemRequest> orderItemList;
}
