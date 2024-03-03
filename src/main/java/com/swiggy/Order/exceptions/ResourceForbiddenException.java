package com.swiggy.Order.exceptions;

public class ResourceForbiddenException extends RuntimeException{
    public ResourceForbiddenException(String message) {
        super(message);
    }
}
