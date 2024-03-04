package com.swiggy.Order.entities;

import com.swiggy.Order.clients.DeliveryPartnerAssignmentManager;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Order(Long customerId, Restaurant restaurant, List<OrderItem> orderItemList) {
        this.customerId = customerId;
        this.restaurantID = restaurant.getId();
        this.orderItemList = orderItemList;
        this.creationTimestamp = LocalDateTime.now();
        calculateBill();
        assignDeliveryPartner(restaurant.getLocation());
    }

    private void calculateBill(){
        for(OrderItem orderItem:orderItemList){
            billTotal+=orderItem.getTotalPrice();
        }
    }
    private void assignDeliveryPartner(Location location){
         deliveryPartnerId = (long) DeliveryPartnerAssignmentManager
                 .getNearestDeliveryPartner(location.getXcordinate(),location.getXcordinate());
    }
}
