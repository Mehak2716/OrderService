package com.swiggy.Order.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long OrderItemID;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private Long menuItemId;
    private int quantity;

}
