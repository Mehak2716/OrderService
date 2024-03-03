package com.swiggy.Order.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Data
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double xcordinate;

    private double ycordinate;

    public Location(double xcordinate, double ycordinate) {
        this.xcordinate = xcordinate;
        this.ycordinate = ycordinate;
    }
    @Override
    public String toString() {
        return "Location{" +
                "ID=" + id +
                ", xCordinate=" + xcordinate +
                ", yCordinate=" + ycordinate +
                '}';
    }
}
