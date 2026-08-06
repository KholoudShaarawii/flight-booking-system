package com.flightbooking.administration.airline.dto.request;

import com.flightbooking.administration.airline.enums.AirlineStatus;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirlinePageRequest {
    @Min(value = 0, message = "Page number must be zero or greater")
    private int page = 0;

    @Min(value = 1, message = "Page size must be greater than zero")
    private int size = 10;

    private AirlineStatus status;

    private String keyword;
}
