package com.swiggy.Order.exceptions;

public class NoAvailableDeliveryPartnerException extends RuntimeException{

    public NoAvailableDeliveryPartnerException(String message) {
        super(message);
    }
}
