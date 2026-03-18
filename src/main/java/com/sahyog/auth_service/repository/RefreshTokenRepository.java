package com.sahyog.auth_service.repository;

import com.sahyog.auth_service.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findByUserId(Long id);

    Optional<RefreshToken> findByUserIdAndDeviceInfo(Long userId,String deviceInfo);

    void deleteByUserId(Long userId);

    void deleteByToken(String token);

    List<RefreshToken> findByUserIdAndRevokedFalse(Long userId);

    void deleteByExpiresAtBefore(LocalDateTime expiresAt);


}
