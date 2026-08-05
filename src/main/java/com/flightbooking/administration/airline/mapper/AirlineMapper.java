package com.flightbooking.administration.airline.mapper;

import com.flightbooking.administration.airline.dto.response.AirlineResponse;
import com.flightbooking.administration.airline.entity.Airline;
import org.springframework.stereotype.Component;

@Component
public class AirlineMapper {
    public AirlineResponse toResponse (Airline airline) {

        return new AirlineResponse(
                airline.getAirlineId(),
                airline.getAirlineName(),
                airline.getContactNumber(),
                airline.getOperatingRegion(),
                airline.getAirlineStatus()
        );
    }

}
