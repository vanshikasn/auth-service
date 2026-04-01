package com.sahyog.auth_service.service;


import com.sahyog.auth_service.dto.request.LoginRequest;
import com.sahyog.auth_service.dto.request.RefreshTokenRequest;
import com.sahyog.auth_service.dto.request.RegisterRequest;
import com.sahyog.auth_service.dto.response.AuthResponse;

public interface AuthService {

    /**
     * Register a new user
     * @param request registration details
     * @return authentication response with tokens
     */
    AuthResponse register(RegisterRequest request);

    /**
     * Login user
     * @param request login credentials
     * @return authentication response with tokens
     */
    AuthResponse login(LoginRequest request);

    /**
     * Refresh access token
     * @param request refresh token
     * @return new authentication response with new access token
     */
    AuthResponse refreshToken(RefreshTokenRequest request);

    /**
     * Logout user (invalidate refresh token)
     * @param userId user ID
     */
    void logout(Long userId);
}