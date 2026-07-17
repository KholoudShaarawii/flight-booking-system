package com.flightbooking.common.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ApiResponseTest {
    @Test
    void successWithData_ShouldCreateSuccessfulResponse() {

        // Arrange
        String message = "Success";
        String data = "data";

        // Act
        ApiResponse<String> response = ApiResponse.success(message, data);

        // Assert
        assertAll(
                () -> assertTrue(response.isSuccess()),
                () -> assertEquals(message, response.getMessage()),
                () -> assertEquals(data, response.getData())
        );
    }

    @Test
    void successWithoutData_ShouldCreateResponseWithNullData() {

        String message = "Success";

        ApiResponse<Void> response = ApiResponse.success(message);

        assertAll(
                () -> assertTrue(response.isSuccess()),
                () -> assertEquals(message, response.getMessage()),
                () -> assertNull(response.getData())
        );
    }
}
