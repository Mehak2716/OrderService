package com.swiggy.Order.models;

import com.swiggy.Order.entities.Location;
import com.swiggy.Order.enums.RestaurantStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    private Long id;

    private String name;

    private Location location;

    private RestaurantStatus restaurantStatus;
}
