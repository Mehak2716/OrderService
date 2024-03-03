package com.swiggy.Order.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @Column(unique = true,nullable = false)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    public Customer(String name, String password,Location location) {
        this.name = name;
        this.password = password;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", location=" + location +
                '}';
    }
}
