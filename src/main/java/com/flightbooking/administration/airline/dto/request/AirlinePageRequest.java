package com.flightbooking.administration.airline.dto.request;

import com.flightbooking.administration.airline.enums.AirlineStatus;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirlinePageRequest {
    @Min(value = 0, message = "Page number must be zero or greater")
    private int page = 0; // Default page number

    @Min(value = 1, message = "Page size must be greater than zero")
    private int size = 10; // Default number of airlines per page

    private AirlineStatus status; // Optional enum filter, not a raw String

    private String keyword; // Optional search by airline name or region
}
