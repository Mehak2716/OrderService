package com.swiggy.Order.entities;

import com.swiggy.Order.clients.DeliveryManager;
import com.swiggy.Order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "orders")
@Component
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "restaurant_id")
    private Long restaurantID;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItemList;

    private double billTotal;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "creation_timestamp")
    private LocalDateTime creationTimestamp;

    public Order(Customer customer, Restaurant restaurant, List<OrderItem> orderItemList) {
        this.customer = customer;
        this.restaurantID = restaurant.getId();
        this.orderItemList = orderItemList;
        this.status = OrderStatus.CREATED;
        this.creationTimestamp = LocalDateTime.now();
        calculateBill();
    }

    private void calculateBill(){
        for(OrderItem orderItem:orderItemList){
            billTotal+=orderItem.getTotalPrice();
        }
    }
    public void initiateDelivery(Restaurant restaurant){
        DeliveryManager.initiateDelivery(this.Id,this.customer.getLocation(),restaurant.getLocation());
    }
}
