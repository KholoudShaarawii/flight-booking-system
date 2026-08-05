package com.flightbooking.common.exception;

public class DuplicateResourceException extends RuntimeException { //thrown when an operation would create a duplicate resource

    private final String errorCode;

    public DuplicateResourceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
