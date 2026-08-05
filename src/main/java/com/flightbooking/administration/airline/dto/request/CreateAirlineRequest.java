package com.flightbooking.administration.airline.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateAirlineRequest {

    @NotBlank(message = "Airline name is required")
    private String airlineName;

    @NotBlank(message = "Contact number is required")
    private String contactNumber;

    @NotBlank(message = "Operating region is required")
    private String operatingRegion;    //main region where the airline operates


}
