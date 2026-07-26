package com.flightbooking.identity.auth.controller;

import com.flightbooking.common.response.ApiResponse;
import com.flightbooking.identity.auth.dto.RegisterCustomerRequest;
import com.flightbooking.identity.auth.dto.RegisterCustomerResponse;
import com.flightbooking.identity.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ApiResponse<RegisterCustomerResponse> registerCustomer(@RequestBody
                                                                  @Valid RegisterCustomerRequest request) {
        RegisterCustomerResponse registrationResponse = authService.registerCustomer(request);

        return ApiResponse.success("Customer registered successfully", registrationResponse);
    }
}
