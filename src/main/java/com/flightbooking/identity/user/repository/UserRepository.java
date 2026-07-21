package com.flightbooking.identity.user.repository;

import com.flightbooking.identity.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); //Serves Login and Current User

    Boolean existsByEmail(String email); //Serves Registration
}

