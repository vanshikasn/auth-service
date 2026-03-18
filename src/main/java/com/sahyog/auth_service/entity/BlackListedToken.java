package com.sahyog.auth_service.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "blacklisted_tokens",
indexes = {
        @Index(name = "idx_token",columnList = "token"),
        @Index(name = "idx_user_id",columnList = "user_id"),
        @Index(name = "idx_expires_at",columnList = "expires_at")
})
public class BlackListedToken {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true, columnDefinition = "TEXT")
    private String token;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @CreationTimestamp
    @Column(nullable = false,name = "blacklisted_at")
    private LocalDateTime blacklistedAt;

    @Column(nullable = false,name = "expires_at")
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(length = 50,nullable = false)
    private BlacklistReason reason;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(length = 500,name = "device_info")
    private String deviceInfo;

    public  BlackListedToken(){}



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BlackListedToken(Long id, String token, Long userId, LocalDateTime expiresAt, BlacklistReason reason, String ipAddress, String deviceInfo) {
        this.id = id;
        this.token = token;
        this.userId = userId;
        this.expiresAt = expiresAt;
        this.reason = reason;
        this.ipAddress = ipAddress;
        this.deviceInfo = deviceInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long user_id) {
        this.userId = user_id;
    }

    public LocalDateTime getBlacklistedAt() {
        return blacklistedAt;
    }

    public void setBlacklistedAt(LocalDateTime blacklistedAt) {
        this.blacklistedAt = blacklistedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public BlacklistReason getReason() {
        return reason;
    }

    public void setReason(BlacklistReason reason) {
        this.reason = reason;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
