package com.sahyog.auth_service.service;

import com.sahyog.auth_service.dto.request.RegisterRequest;
import com.sahyog.auth_service.entity.User;

import java.util.Optional;

public interface UserService {

    /**
     * Find user by username
     * @param username the username to search for
     * @return Optional containing user if found, empty otherwise
     */
    Optional<User> findByUsername(String username);

    /**
     * Find user by email
     * @param email the email to search for
     * @return Optional containing user if found, empty otherwise
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by username or email
     * @param usernameOrEmail the username or email to search for
     * @return Optional containing user if found, empty otherwise
     */
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);

    /**
     * Check if username already exists
     * @param username the username to check
     * @return true if exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if email already exists
     * @param email the email to check
     * @return true if exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Create a new user from registration request
     * @param request the registration request containing user details
     * @return the created user
     */
    User createUser(RegisterRequest request);

    /**
     * Get user by ID
     * @param id the user ID
     * @return Optional containing user if found, empty otherwise
     */
    Optional<User> findById(Long id);
}