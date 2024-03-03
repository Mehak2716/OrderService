package com.swiggy.Order.exceptions;

public class UsernameAlreadyInUseException extends RuntimeException{

    public UsernameAlreadyInUseException(String message) {
        super(message);
    }
}
