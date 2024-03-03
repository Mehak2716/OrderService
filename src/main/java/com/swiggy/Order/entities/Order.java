package com.swiggy.Order.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Column(name = "restaurant_id")
    private int restaurantID;
    private String description;
    private double billTotal;
    @Column(name = "creation_timestamp")
    private LocalDateTime creationTimestamp;

}
