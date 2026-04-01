package com.sahyog.auth_service.service;

import com.sahyog.auth_service.entity.User;
import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.function.Function;

/**
 * Service interface for JWT operations
 * Handles token generation, validation, and extraction
 */
public interface JwtService {

    /**
     * Generate access token for authenticated user
     * @param user - authenticated user
     * @return JWT access token (short-lived)
     */
    String generateAccessToken(User user);

    /**
     * Generate refresh token for authenticated user
     * @param user - authenticated user
     * @return JWT refresh token (long-lived)
     */
    String generateRefreshToken(User user);

    /**
     * Extract username from JWT token
     * @param token - JWT token
     * @return username
     */
    String extractUsername(String token);

    /**
     * Extract expiration date from JWT token
     * @param token - JWT token
     * @return expiration date
     */
    Date extractExpiration(String token);

    /**
     * Validate JWT token
     * @param token - JWT token
     * @param user - user to validate against
     * @return true if valid, false otherwise
     */
    boolean validateToken(String token, User user);

    /**
     * Check if token is expired
     * @param token - JWT token
     * @return true if expired, false otherwise
     */
    boolean isTokenExpired(String token);

    /**
     * Extract all claims from token
     * @param token - JWT token
     * @return Claims object
     */
    Claims extractAllClaims(String token);

    /**
     * Extract specific claim from token
     * @param token - JWT token
     * @param claimsResolver - function to extract specific claim
     * @return extracted claim
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
}