package com.flightbooking.common.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void error_ShouldCreateErrorResponseWithEmptyDetails() {

        String message = "Business Error";
        String errorCode = "BUSINESS_ERROR";

        ErrorResponse response =
                ErrorResponse.error(message, errorCode);

        assertAll(
                () -> assertFalse(response.isSuccess()),
                () -> assertEquals(message, response.getMessage()),
                () -> assertEquals(errorCode, response.getErrorCode()),
                () -> assertNotNull(response.getDetails()),
                () -> assertTrue(response.getDetails().isEmpty())
        );
    }

    @Test
    void validationError_WithDetails_ShouldCreateValidationErrorResponse() {

        List<String> details =
                List.of("Validation Error Details");

        ErrorResponse response =
                ErrorResponse.validationError(details);

        assertAll(
                () -> assertFalse(response.isSuccess()),
                () -> assertEquals("Validation failed", response.getMessage()),
                () -> assertEquals("VALIDATION_ERROR", response.getErrorCode()),
                () -> assertEquals(details, response.getDetails())
        );
    }
}