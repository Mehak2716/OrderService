package com.swiggy.Order.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private Long customerId;
    @Column(name = "restaurant_id")
    private Long restaurantID;
    @Column(name = "delivery_partner_id")
    private Long deliveryPartnerId;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItemList;
    private double billTotal;
    @Column(name = "creation_timestamp")
    private LocalDateTime creationTimestamp;

    public Order(Long customerId, Long restaurantID, List<OrderItem> orderItemList) {
        this.customerId = customerId;
        this.restaurantID = restaurantID;
        this.orderItemList = orderItemList;
        this.creationTimestamp = LocalDateTime.now();
        calculateBill();
        assignDeliveryPartner();
    }

    public void calculateBill(){
        for(OrderItem orderItem:orderItemList){
            billTotal+=orderItem.getTotalPrice();
        }
    }
    public void assignDeliveryPartner(){

    }
}
