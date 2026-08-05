package com.flightbooking.administration.airline.entity;

import com.flightbooking.administration.airline.enums.AirlineStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "AIRLINES",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_AIRLINES_NAME",
                        columnNames = "AIRLINE_NAME")})
@Setter
@Getter
public class Airline {

    @Id
    @SequenceGenerator(name = "airlineSeqGenerator", sequenceName = "AIRLINE_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "airlineSeqGenerator")
    private Long airlineId;

    @Column(name = "AIRLINE_NAME", nullable = false, length = 100)
    private String airlineName;

    @Column(nullable = false, length = 50)
    private String contactNumber;

    @Column(nullable = false, length = 50)
    private String operatingRegion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AirlineStatus airlineStatus;
}
