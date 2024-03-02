package com.swiggy.Order.mapper;

import com.swiggy.Order.dto.CustomerDto;
import com.swiggy.Order.entities.Customer;
import com.swiggy.Order.entities.Location;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.awt.*;

public class CustomerMapper {

    public static Customer mapToCustomer(CustomerDto customerDto){

        Location location = LocationMapper.mapToLocation(customerDto.getLocation());
        Customer customer = new Customer(
                customerDto.getName(),
                customerDto.getPassword(),
                location);

        return customer;
    }
}
