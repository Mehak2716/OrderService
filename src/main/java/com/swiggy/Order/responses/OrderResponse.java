package com.swiggy.Order.responses;

import com.swiggy.Order.entities.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long orderId;

    private Long customerId;

    private Long restaurantID;

    private Long deliveryPartnerId;

    private List<OrderItemResponse> orderedItems;

    private double totalBill;

    private LocalDateTime creationTimestamp;

}
