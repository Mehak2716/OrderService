package com.swiggy.Order.dto.requests;

import com.swiggy.Order.entities.Location;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    private String username;

    private String password;

    private Location location;
}
