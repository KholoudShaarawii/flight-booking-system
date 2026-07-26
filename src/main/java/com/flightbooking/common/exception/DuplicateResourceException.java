package com.flightbooking.common.exception;

public class DuplicateResourceException extends RuntimeException { //duplicate email , custom exception


    public DuplicateResourceException(String message) {
        super(message);
    }
}
