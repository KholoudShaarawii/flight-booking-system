package com.flightbooking.administration.airline.controller;

import com.flightbooking.administration.airline.dto.request.AirlinePageRequest;
import com.flightbooking.administration.airline.dto.response.AirlineResponse;
import com.flightbooking.administration.airline.dto.request.CreateAirlineRequest;
import com.flightbooking.administration.airline.dto.request.UpdateAirlineRequest;
import com.flightbooking.administration.airline.service.AirlineService;
import com.flightbooking.common.response.ApiResponse;
import com.flightbooking.common.response.PageResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/airlines")
public class AirlineController {
    private final AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @PostMapping
    public ApiResponse<AirlineResponse> createAirline(@Valid
                                                      @RequestBody CreateAirlineRequest request) {
        AirlineResponse response = airlineService.createAirline(request);

        return ApiResponse.success("Airline created successfully", response);
    }

    @PatchMapping("/{airlineId}")
    public ApiResponse<AirlineResponse> updateAirline(@PathVariable("airlineId") Long airlineId,
                                                      @Valid @RequestBody UpdateAirlineRequest request) {

        AirlineResponse response = airlineService.updateAirline(airlineId, request);

        return ApiResponse.success("Airline updated successfully", response);

    }

    @PatchMapping("/{airlineId}/deactivate")
    public ApiResponse<AirlineResponse> deactivateAirline(@PathVariable("airlineId") Long airlineId) {
        AirlineResponse response = airlineService.deactivateAirline(airlineId);

        return ApiResponse.success("Airline deactivated successfully", response);

    }

    //GET /api/airlines?page=0&size=10&status=ACTIVE&keyword=egypt
    @GetMapping
    public ApiResponse<PageResponse<AirlineResponse>> getAllAirlines(@Valid
                                                                     @ModelAttribute AirlinePageRequest request) {

        PageResponse<AirlineResponse> response = airlineService.getAllAirlines(request);
        return ApiResponse.success(
                "Airlines retrieved successfully",
                response
        );
    }

}