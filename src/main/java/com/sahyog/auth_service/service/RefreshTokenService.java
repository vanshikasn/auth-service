package com.sahyog.auth_service.service;

import com.sahyog.auth_service.entity.RefreshToken;
import com.sahyog.auth_service.entity.User;

import java.util.Optional;

public interface RefreshTokenService {

    /**
     * Create a new refresh token for a user
     * @param user the user to create token for
     * @return the created refresh token
     */
    RefreshToken createRefreshToken(User user);

    /**
     * Find refresh token by token string
     * @param token the token string
     * @return Optional containing token if found
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Verify if refresh token is expired
     * @param token the refresh token to verify
     * @return the token if valid
     * @throws RuntimeException if token is expired
     */
    RefreshToken verifyExpiration(RefreshToken token);

    /**
     * Delete all expired tokens
     * @return number of tokens deleted
     */
    int deleteExpiredTokens();

    /**
     * Delete all tokens for a specific user
     * @param userId the user ID
     */
    void deleteByUserId(Long userId);
}