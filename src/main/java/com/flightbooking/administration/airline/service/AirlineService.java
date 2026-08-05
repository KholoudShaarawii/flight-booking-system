package com.flightbooking.administration.airline.service;

import com.flightbooking.administration.airline.dto.request.AirlinePageRequest;
import com.flightbooking.administration.airline.dto.response.AirlineResponse;
import com.flightbooking.administration.airline.dto.request.CreateAirlineRequest;
import com.flightbooking.administration.airline.dto.request.UpdateAirlineRequest;
import com.flightbooking.administration.airline.entity.Airline;
import com.flightbooking.administration.airline.enums.AirlineStatus;
import com.flightbooking.administration.airline.mapper.AirlineMapper;
import com.flightbooking.administration.airline.repository.AirlineRepository;
import com.flightbooking.administration.airline.specification.AirlineSpecification;
import com.flightbooking.common.exception.BadRequestException;
import com.flightbooking.common.exception.DuplicateResourceException;
import com.flightbooking.common.exception.ResourceNotFoundException;
import com.flightbooking.common.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AirlineService {

    private final AirlineRepository airlineRepository;
    private final AirlineMapper airlineMapper;

    public AirlineService(AirlineRepository airlineRepository, AirlineMapper airlineMapper) {
        this.airlineRepository = airlineRepository;
        this.airlineMapper = airlineMapper;
    }

    public AirlineResponse createAirline(CreateAirlineRequest request) {

        String requestedAirlineName = request.getAirlineName().trim();

        if (airlineRepository.existsByAirlineNameIgnoreCase(requestedAirlineName)) {
            throw new DuplicateResourceException("Airline already exists", "DUPLICATE_AIRLINE");
        }

        Airline airline = new Airline();
        airline.setAirlineName(requestedAirlineName);
        airline.setContactNumber(request.getContactNumber());
        airline.setOperatingRegion(request.getOperatingRegion());
        airline.setAirlineStatus(AirlineStatus.ACTIVE);

        Airline savedAirline = airlineRepository.save(airline);
        return airlineMapper.toResponse(savedAirline);
    }

    public AirlineResponse updateAirline(Long airlineId, UpdateAirlineRequest request) {

        if (request.getAirlineName() == null
                && request.getContactNumber() == null
                && request.getOperatingRegion() == null) {

            throw new BadRequestException("At least one field must be provided");
        }

        Airline airline = airlineRepository.findById(airlineId)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found"));

        if (request.getAirlineName() != null) {
            String requestedAirlineName = request.getAirlineName().trim();

            //Airline name is unique
            if (airlineRepository.existsByAirlineNameIgnoreCaseAndAirlineIdNot(requestedAirlineName, airlineId)) {
                throw new DuplicateResourceException("Airline already exists","DUPLICATE_AIRLINE");
            }
            airline.setAirlineName(requestedAirlineName);
        }

        if (request.getContactNumber() != null) {
            airline.setContactNumber(request.getContactNumber().trim());
        }

        if (request.getOperatingRegion() != null) {
            airline.setOperatingRegion(request.getOperatingRegion().trim());
        }

        Airline savedAirline = airlineRepository.save(airline);
        return airlineMapper.toResponse(savedAirline);

    }

    public AirlineResponse deactivateAirline(Long airlineId) {
        Airline airline = airlineRepository.findById(airlineId)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found"));

        airline.setAirlineStatus(AirlineStatus.INACTIVE);

        Airline savedAirline = airlineRepository.save(airline);
        return airlineMapper.toResponse(savedAirline);

    }


    public PageResponse<AirlineResponse> getAllAirlines(AirlinePageRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(),
                request.getSize(),
                Sort.by("airlineId").ascending());

        Specification<Airline> specification = AirlineSpecification.hasStatus(request.getStatus())
                .and(AirlineSpecification.hasKeyword(request.getKeyword()));

        Page<Airline> airlinePage = airlineRepository.findAll(specification, pageable);

        Page<AirlineResponse> responsePage = airlinePage.map(airlineMapper::toResponse);

        return PageResponse.of(
                responsePage.getContent(),
                responsePage.getNumber(),
                responsePage.getSize(),
                responsePage.getTotalElements(),
                responsePage.getTotalPages()
        );
    }


}



