package com.swiggy.Order.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long OrderItemID;

    private Long menuItemId;

    private int quantity;

    @Column(name = "order_item_price")
    private double totalPrice;

    public OrderItem(Long menuItemId, int quantity, double totalPrice){
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
