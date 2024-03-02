package com.swiggy.Order.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {

    private String name;
    private int x_coordinate;
    private int y_coordinate;
}
