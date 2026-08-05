package com.flightbooking.administration.airline.repository;

import com.flightbooking.administration.airline.entity.Airline;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AirlineRepository extends JpaRepository<Airline, Long>, JpaSpecificationExecutor<Airline> {

    boolean existsByAirlineNameIgnoreCase(String airlineName);

    boolean existsByAirlineNameIgnoreCaseAndAirlineIdNot(String airlineName, Long airlineId);

    //Page<Airline> findAll(Specification<Airline> specification, Pageable pageable);
}
