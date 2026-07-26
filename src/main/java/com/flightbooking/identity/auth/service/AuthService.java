package com.flightbooking.identity.auth.service;

import com.flightbooking.common.exception.DuplicateResourceException;
import com.flightbooking.identity.auth.dto.RegisterCustomerRequest;
import com.flightbooking.identity.auth.dto.RegisterCustomerResponse;
import com.flightbooking.identity.user.entity.User;
import com.flightbooking.identity.user.enums.UserRole;
import com.flightbooking.identity.user.enums.UserStatus;
import com.flightbooking.identity.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterCustomerResponse registerCustomer(RegisterCustomerRequest registerCustomerRequest) {

        if (userRepository.existsByEmail(registerCustomerRequest.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(registerCustomerRequest.getPassword());

        User user = new User();
        user.setFirstName(registerCustomerRequest.getFirstName());
        user.setLastName(registerCustomerRequest.getLastName());
        user.setEmail(registerCustomerRequest.getEmail());
        user.setPhone(registerCustomerRequest.getPhone());

        user.setPassword(encodedPassword);
        user.setRole(UserRole.CUSTOMER);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setAirlineId(null);

        User savedUser = userRepository.save(user);
        return new RegisterCustomerResponse(
                savedUser.getUserId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail(),
                savedUser.getPhone(),
                savedUser.getRole(),
                savedUser.getUserStatus()
        );

    }
}

