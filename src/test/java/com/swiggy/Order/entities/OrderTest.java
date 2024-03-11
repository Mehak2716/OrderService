package com.swiggy.Order.entities;

import com.swiggy.Order.constants.enums.RestaurantStatus;
import com.swiggy.Order.dto.data.Restaurant;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class OrderTest {

    @Test
    public void testOrderCreatedSuccessfully(){
        List<OrderItem> orderItemList = new ArrayList<>(List.of(new OrderItem(1L,2L,10,20)));
        Customer customer = new Customer("testUser","testPassword",new Location());
        Restaurant restaurant = new Restaurant(1L, "testRestaurant",new Location(), RestaurantStatus.Open);
         assertDoesNotThrow(()->{
           Order order = new Order(customer,restaurant,orderItemList);
        });
    }

    @Test
    public void testOrderBillSuccessfully(){
        OrderItem firstOrderItem = spy(new OrderItem(1L,2L,10,20));
        OrderItem secondOrderItem = spy(new OrderItem(2L,2L,20,40));

        List<OrderItem> orderItemList = new ArrayList<>(List.of(firstOrderItem,secondOrderItem));
        Customer customer = new Customer("testUser","testPassword",new Location());
        Restaurant restaurant = new Restaurant(1L, "testRestaurant",new Location(), RestaurantStatus.Open);
        Order order = new Order(customer,restaurant,orderItemList);

        verify(firstOrderItem,times(1)).getTotalPrice();
        verify(secondOrderItem,times(1)).getTotalPrice();
    }
}
