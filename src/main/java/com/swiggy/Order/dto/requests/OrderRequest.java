package com.swiggy.Order.dto.requests;

import com.swiggy.Order.dto.data.Restaurant;
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
