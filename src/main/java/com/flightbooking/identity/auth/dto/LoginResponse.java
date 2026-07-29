package com.flightbooking.identity.auth.dto;

import com.flightbooking.identity.user.enums.UserRole;
import com.flightbooking.identity.user.enums.UserStatus;
import lombok.Getter;

@Getter
public class LoginResponse { //Token information + Authenticated user information

    private final String accessToken;
    private final String tokenType;  //types?
    private final LoginUserResponse user;

    public LoginResponse(String accessToken, String tokenType, LoginUserResponse user) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.user = user;
    }
}
/*LoginResponse
├── accessToken
├── tokenType
└── user */
/*{
  "accessToken": "jwt-access-token",
  "tokenType": "Bearer",
  "user": {
    "userId": 1,
    "firstName": "Ahmed",
    "lastName": "Ali",
    "email": "ahmed@example.com",
    "role": "CUSTOMER",
    "userStatus": "ACTIVE",
    "airlineId": null
  }
}*/