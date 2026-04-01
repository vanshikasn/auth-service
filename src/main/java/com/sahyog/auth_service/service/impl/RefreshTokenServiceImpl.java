package com.sahyog.auth_service.service.impl;

import com.sahyog.auth_service.config.JwtProperties;
import com.sahyog.auth_service.entity.RefreshToken;
import com.sahyog.auth_service.entity.User;
import com.sahyog.auth_service.repository.RefreshTokenRepository;
import com.sahyog.auth_service.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtProperties jwtProperties;

    //Creates a new refresh token for a user when they log in.
    @Override
    public RefreshToken createRefreshToken(User user) {
        deleteByUserId(user.getId());
        RefreshToken refreshToken=new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        LocalDateTime expiryDate = LocalDateTime.now()
                .plusSeconds(jwtProperties.getRefreshToken().getExpiration() / 1000);
        refreshToken.setExpiresAt(expiryDate);
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired. Please login again.");
        }
        return token;
    }

    @Override
    public int deleteExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        return refreshTokenRepository.deleteByExpiresAtBefore(now);
    }

    @Override
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}
