package com.swiggy.Order.entities;

import com.swiggy.Order.enums.Status;
import lombok.Data;

@Data
public class Restaurant {

    private Long id;

    private String name;

    private Location location;

    private Status status;
}
