package com.flightbooking.identity.auth.dto;


import com.flightbooking.identity.user.enums.UserRole;
import com.flightbooking.identity.user.enums.UserStatus;
import lombok.Getter;

@Getter
public class LoginUserResponse {

    private final Long userId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final UserRole role;
    private final UserStatus userStatus;
    private final Long airlineId;

    public LoginUserResponse(
            Long userId,
            String firstName,
            String lastName,
            String email,
            UserRole role,
            UserStatus userStatus,
            Long airlineId
    ) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.userStatus = userStatus;
        this.airlineId = airlineId;
    }
}