package com.flightbooking.administration.airline.specification;

import com.flightbooking.administration.airline.entity.Airline;
import com.flightbooking.administration.airline.enums.AirlineStatus;
import org.springframework.data.jpa.domain.Specification;

public final class AirlineSpecification { // builds dynamic query conditions for filtering airlines
                                         //a status,a keyword, both criteria, or neither to retrieve all airlines.

    private AirlineSpecification() {
    }

    // Filter by status
    public static Specification<Airline> hasStatus(AirlineStatus status) {
        return (root, query, cb) -> {
            // If status was not provided, do not filter by status
            if (status == null) {
                return cb.conjunction(); // Always true - no filter
            }
            return cb.equal(root.get("airlineStatus"), status);
        };
    }

    // Filter by keyword
    public static Specification<Airline> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) {
                return cb.conjunction();
            }
           // Prepare the search pattern
            String searchPattern = "%" + keyword.trim().toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("airlineName")),
                            searchPattern),
                    cb.like(cb.lower(root.get("operatingRegion")),
                            searchPattern)
            );
        };
    }
}


