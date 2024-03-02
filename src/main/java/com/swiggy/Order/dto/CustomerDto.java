package com.swiggy.Order.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private String name;

    private String password;

    private LocationDto location;

}
