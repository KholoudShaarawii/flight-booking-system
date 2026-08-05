package com.flightbooking.administration.airline.dto.response;

import com.flightbooking.administration.airline.enums.AirlineStatus;
import lombok.Getter;

@Getter
public class AirlineResponse {
    private final Long airlineId;
    private final String airlineName;
    private final String contactNumber;
    private final String operatingRegion;
    private final AirlineStatus airlineStatus;


    public AirlineResponse(Long airlineId, String airlineName, String contactNumber, String operatingRegion, AirlineStatus airlineStatus) {
        this.airlineId = airlineId;
        this.airlineName = airlineName;
        this.contactNumber = contactNumber;
        this.operatingRegion = operatingRegion;
        this.airlineStatus = airlineStatus;
    }
}
