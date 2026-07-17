package com.flightbooking.common.response;

import java.util.List;

import lombok.Getter;

@Getter
public final class ErrorResponse { //Error + Validation Error

    private final boolean success;
    private final String message;
    private final String errorCode;
    private final List<String> details;

    private ErrorResponse( String message, String errorCode, List<String> details) {
        this.success = false;
        this.message = message;
        this.errorCode = errorCode;
        this.details = details;
    }

    public static ErrorResponse error(String message, String errorCode ) {
        return new ErrorResponse(message, errorCode, List.of()); //immutable
    }


    public static ErrorResponse validationError(List<String> details) {
        return new ErrorResponse("Validation failed", "VALIDATION_ERROR", details);
    }
}
/*{
  "success": false,
  "message": "Something went wrong",
  "errorCode": "ERROR_CODE",
  "details": []
}*/
