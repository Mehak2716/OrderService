package com.swiggy.Order.repositories;

import com.swiggy.Order.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
