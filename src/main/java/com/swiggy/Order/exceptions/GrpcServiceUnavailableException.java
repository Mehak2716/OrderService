package com.swiggy.Order.exceptions;

public class GrpcServiceUnavailableException extends RuntimeException{

    public GrpcServiceUnavailableException(String message) {
        super(message);
    }
}
