package com.flightbooking.identity.auth.dto.response;

import com.flightbooking.identity.user.enums.UserRole;
import com.flightbooking.identity.user.enums.UserStatus;
import lombok.Getter;

@Getter
public class RegisterCustomerResponse { //ResponseEntity<ApiResponse<RegisterCustomerResponse>>
    private final Long userId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phone;
    private final UserRole role;
    private final UserStatus userStatus;

    public RegisterCustomerResponse(Long userId, String firstName, String lastName, String email, String phone, UserRole role, UserStatus userStatus) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.userStatus = userStatus;
    }
}
/*
ApiResponse<RegisterCustomerResponse>
├── success = true
├── message = "Customer registered successfully"
└── data
    └── RegisterCustomerResponse
------
{
  "userId": 1,
  "firstName": "Ahmed",
  "lastName": "Ali",
  "email": "ahmed@example.com",
  "phone": "+201001234567",
  "role": "CUSTOMER",
  "userStatus": "ACTIVE"
}
------
 {
  "success": false,
  "message": "Email already exists",
  "errorCode": "DUPLICATE_EMAIL",
  "details": []
}*/