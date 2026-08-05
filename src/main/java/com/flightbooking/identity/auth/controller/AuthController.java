package com.flightbooking.identity.auth.controller;

import com.flightbooking.common.response.ApiResponse;
import com.flightbooking.identity.auth.dto.request.ChangePasswordRequest;
import com.flightbooking.identity.auth.dto.request.LoginRequest;
import com.flightbooking.identity.auth.dto.request.RegisterCustomerRequest;
import com.flightbooking.identity.auth.dto.request.UpdateProfileRequest;
import com.flightbooking.identity.auth.dto.response.CurrentUserResponse;
import com.flightbooking.identity.auth.dto.response.LoginResponse;
import com.flightbooking.identity.auth.dto.response.RegisterCustomerResponse;
import com.flightbooking.identity.auth.service.AuthService;
import com.flightbooking.identity.security.principal.CustomUserPrincipal;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

//@Controller
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ApiResponse<RegisterCustomerResponse> registerCustomer(@Valid
                                                                  @RequestBody RegisterCustomerRequest request) {
        RegisterCustomerResponse registrationResponse = authService.registerCustomer(request);

        return ApiResponse.success("Customer registered successfully", registrationResponse);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> loginUser(@Valid
                                            @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.loginUser(request);

        return ApiResponse.success("Login successful", loginResponse);
    }

    @GetMapping("/me")
    public ApiResponse<CurrentUserResponse> getCurrentUserProfile(@AuthenticationPrincipal CustomUserPrincipal principal) {
        CurrentUserResponse response = authService.getCurrentUserProfile(principal.getUserId());

        return ApiResponse.success("User profile retrieved successfully", response);
    }

    @PatchMapping("/me")
    public ApiResponse<CurrentUserResponse> updateCurrentUserProfile(@AuthenticationPrincipal CustomUserPrincipal principal,
                                                              @Valid @RequestBody UpdateProfileRequest request) {
        CurrentUserResponse response = authService.updateCurrentUserProfile(principal.getUserId(), request);

        return ApiResponse.success("User profile updated successfully", response);
    }

    @PutMapping ("/change-password")
    public ApiResponse<Void> changeOldPassword(@AuthenticationPrincipal CustomUserPrincipal principal,
                                            @Valid @RequestBody ChangePasswordRequest request) {
        authService.changeOldPassword(principal.getUserId(), request);
        return ApiResponse.success("Password changed successfully", null);
    }

}
