package com.sahyog.auth_service.repository;

import com.sahyog.auth_service.entity.BlackListedToken;
import com.sahyog.auth_service.entity.BlacklistReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BlacklistedTokenRepository extends JpaRepository<BlackListedToken,Long> {

    Optional<BlackListedToken> findByToken(String token);

    boolean existsByToken(String token);

    List<BlackListedToken> findByUserId(Long userId);

    void deleteByExpiresAtBefore(LocalDateTime expiresAt);

    List<BlackListedToken> findByUserIdAndReason(Long userId, BlacklistReason reason);

    Long countByUserId(Long userId);

    List<BlackListedToken> findByBlacklistedAtBetween(LocalDateTime start,LocalDateTime end);

}
