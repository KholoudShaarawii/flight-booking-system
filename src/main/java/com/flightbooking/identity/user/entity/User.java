package com.flightbooking.identity.user.entity;

import com.flightbooking.identity.user.enums.UserRole;
import com.flightbooking.identity.user.enums.UserStatus;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "USERS",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_USERS_EMAIL",
                        columnNames = "EMAIL")
        })
public class User {

    @Id
    @SequenceGenerator(name = "usersSeqGenerator", sequenceName = "USERS_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersSeqGenerator")
    private Long userId;

    //
    private Long airlineId;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @PrePersist
    private void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}