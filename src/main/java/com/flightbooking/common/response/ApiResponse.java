package com.flightbooking.common.response;

import lombok.Getter;

@Getter
public final class ApiResponse<T> { //success


  private final boolean success;
    private final String message;
    private final T data;

    private ApiResponse( String message, T data) {
        this.success = true;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>( message, data);
    }

    // 2) Success without Data
    public static ApiResponse<Void> success(String message) {

        return success(message, null);
    }
}

/*{
  "success": true,
  "message": "Any success message",
  "data": "Any data depending on the API"
}*/
/*Single Responsibility + Open/Closed + Static Factory Method Pattern + DRY Principle*/