package com.swiggy.Order.exceptions;

public class OrderNotFoundException extends  RuntimeException{
    public OrderNotFoundException(String message) {
        super(message);
    }
}
