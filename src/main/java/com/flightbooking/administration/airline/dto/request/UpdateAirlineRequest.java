package com.flightbooking.administration.airline.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateAirlineRequest {

    @Pattern(regexp = ".*\\S.*", message = "Airline name must not be blank")
    private String airlineName;

    @Pattern(regexp = ".*\\S.*", message = "Contact number must not be blank")
    private String contactNumber;

    @Pattern(regexp = ".*\\S.*", message = "Operating region must not be blank")
    private String operatingRegion;
}

