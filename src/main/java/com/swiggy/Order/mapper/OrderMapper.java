package com.swiggy.Order.mapper;

import com.swiggy.Order.entities.Customer;
import com.swiggy.Order.entities.MenuItem;
import com.swiggy.Order.entities.Order;
import com.swiggy.Order.entities.OrderItem;
import com.swiggy.Order.requests.OrderItemRequest;
import com.swiggy.Order.requests.OrderRequest;
import com.swiggy.Order.responses.OrderItemResponse;
import com.swiggy.Order.responses.OrderResponse;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    public static Order mapToOrder(Customer customer, OrderRequest orderRequest) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for(OrderItemRequest orderItemRequest:orderRequest.getOrderItemList()){
            MenuItem menuItem= orderItemRequest.getMenuItem();
            double price = menuItem.getPrice();
            int quantity = orderItemRequest.getQuantity();
            OrderItem orderItem = new OrderItem(menuItem.getId(), quantity, price*quantity);
            orderItemList.add(orderItem);
        }

        return new Order(customer,
                orderRequest.getRestaurant(),
                orderItemList);
    }

    public static OrderResponse mapToResponse(Order order){

        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for (OrderItem orderItem: order.getOrderItemList()){
            OrderItemResponse orderItemResponse = new OrderItemResponse(
                    orderItem.getMenuItemId(),
                    orderItem.getQuantity(),
                    orderItem.getTotalPrice());
            orderItemResponses.add(orderItemResponse);
        }

       return new OrderResponse(
                order.getId(),
                order.getCustomer().getId(),
                order.getRestaurantID(),
                orderItemResponses,
                order.getBillTotal(),
                order.getCreationTimestamp()
        );
    }
}
