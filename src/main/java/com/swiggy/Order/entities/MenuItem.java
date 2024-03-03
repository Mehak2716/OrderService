package com.swiggy.Order.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {
    private Long id;
    private String name;
    private double price;
    private String description;
}
