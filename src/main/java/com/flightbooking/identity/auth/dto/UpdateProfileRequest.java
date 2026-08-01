package com.flightbooking.identity.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateProfileRequest {

    // @NotBlank xx
    @Pattern(regexp = ".*\\S.*", message = "First name must not be blank")
    private String firstName;

    @Pattern(regexp = ".*\\S.*", message = "Last name must not be blank")
    private String lastName;

    private String phone;
}
