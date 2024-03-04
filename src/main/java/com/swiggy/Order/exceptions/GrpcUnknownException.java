package com.swiggy.Order.exceptions;

public class GrpcUnknownException extends RuntimeException{
    public GrpcUnknownException(String message) {
        super(message);
    }
}
