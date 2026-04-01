package com.sahyog.auth_service.service.impl;

import com.sahyog.auth_service.config.JwtProperties;
import com.sahyog.auth_service.entity.User;
import com.sahyog.auth_service.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;  // ⬅️ Changed from java.security.Key
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());

        String userName = user.getUserName();
        long jwtExpirationTime = jwtProperties.getAccessToken().getExpiration();

        return createToken(claims, userName, jwtExpirationTime);
    }

    @Override
    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("tokenType", "REFRESH");

        String userName = user.getUserName();
        long jwtExpirationTime = jwtProperties.getRefreshToken().getExpiration();

        return createToken(claims, userName, jwtExpirationTime);
    }

    private String createToken(Map<String, Object> claims, String userName, long jwtExpirationTime) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationTime);

        return Jwts.builder()
                .claims(claims)              // ⬅️ Removed 'set' prefix
                .subject(userName)           // ⬅️ Removed 'set' prefix
                .issuedAt(now)              // ⬅️ Removed 'set' prefix
                .expiration(expiryDate)     // ⬅️ Removed 'set' prefix
                .signWith(getSigningKey())  // ⬅️ Removed SignatureAlgorithm parameter
                .compact();
    }

    private SecretKey getSigningKey() {  // ⬅️ Changed from Key to SecretKey
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    @Override
    public boolean validateToken(String token, User user) {
        try {
            String username = extractUsername(token);
            return (username.equals(user.getUserName()) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}