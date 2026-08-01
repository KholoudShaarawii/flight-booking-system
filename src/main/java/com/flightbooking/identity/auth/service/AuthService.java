package com.flightbooking.identity.auth.service;

import com.flightbooking.common.exception.BadRequestException;
import com.flightbooking.common.exception.DuplicateResourceException;
import com.flightbooking.common.exception.ResourceNotFoundException;
import com.flightbooking.identity.auth.dto.*;
import com.flightbooking.identity.security.jwt.JwtService;
import com.flightbooking.identity.user.entity.User;
import com.flightbooking.identity.user.enums.UserRole;
import com.flightbooking.identity.user.enums.UserStatus;
import com.flightbooking.identity.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.IllegalFormatCodePointException;
import java.util.Optional;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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

    public LoginResponse loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        boolean PasswordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!PasswordMatches) {
            throw new BadCredentialsException("Invalid email or password");
        }

        if (user.getUserStatus() == UserStatus.BLOCKED) {
            throw new LockedException("Account is blocked");
        }

        if (user.getUserStatus() == UserStatus.DEACTIVATED) {
            throw new DisabledException("Account is deactivated");
        }

        String accessToken = jwtService.generateToken(user);

        LoginUserResponse loginUserResponse = new LoginUserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                user.getUserStatus(),
                user.getAirlineId()
        );

        return new LoginResponse(
                accessToken, "Bearer", loginUserResponse);

    }

    public CurrentUserResponse getCurrentUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new CurrentUserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getUserStatus(),
                user.getAirlineId());

    }

    @Transactional
    public CurrentUserResponse updateCurrentUserProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (request.getFirstName() == null && request.getLastName() == null && request.getPhone() == null) {
            throw new BadRequestException("At least one profile field must be provided");

        }

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        // User savedUser = userRepository.save(user);
        return new CurrentUserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getUserStatus(),
                user.getAirlineId()
        );


    }

    public void changeOldPassword(Long userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        if (!passwordEncoder.matches(request.getOldPassword(),
                user.getPassword())) {
            throw new BadRequestException("Old password is incorrect");

        }

        String encodedPassword = passwordEncoder.encode(request.getNewPassword());

        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
}
