package com.swiggy.Order.mapper;

import com.swiggy.Order.dto.LocationDto;
import com.swiggy.Order.entities.Location;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;


public class LocationMapper {
    public static Location mapToLocation(LocationDto locationDto){

        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(
                new Coordinate(locationDto.getX_coordinate(), locationDto.getY_coordinate()));

        return new com.swiggy.Order.entities.Location(locationDto.getName(), point);
    }
}
