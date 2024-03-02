package com.swiggy.Order.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;


@Entity
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    private String name;
    private Point point;

    public Location(String name, Point point){
        this.name = name;
        this.point = point;
    }

    @Override
    public String toString() {
        return "Location{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", point=" + point.getX() + point.getY()+
                '}';
    }
}
