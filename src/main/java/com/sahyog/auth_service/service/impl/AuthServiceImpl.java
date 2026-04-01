package com.sahyog.auth_service.service.impl;

import com.sahyog.auth_service.config.JwtProperties;
import com.sahyog.auth_service.dto.request.LoginRequest;
import com.sahyog.auth_service.dto.request.RefreshTokenRequest;
import com.sahyog.auth_service.dto.request.RegisterRequest;
import com.sahyog.auth_service.dto.response.AuthResponse;
import com.sahyog.auth_service.dto.response.UserResponse;
import com.sahyog.auth_service.entity.RefreshToken;
import com.sahyog.auth_service.entity.User;
import com.sahyog.auth_service.repository.UserRepository;
import com.sahyog.auth_service.service.AuthService;
import com.sahyog.auth_service.service.JwtService;
import com.sahyog.auth_service.service.RefreshTokenService;
import com.sahyog.auth_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

   @Autowired
   private JwtService jwtService;

   @Autowired
   private RefreshTokenService refreshTokenService;

   @Autowired
   private JwtProperties jwtProperties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if(userService.existsByEmail(request.getEmail())){
            throw new RuntimeException("email already present");
        }
        AuthResponse authResponse=new AuthResponse();
        User user=userService.createUser(request);

        UserResponse userResponse=mapToUserResponse(user);
        authResponse.setUserResponse(userResponse);

        String accessToken= jwtService.generateAccessToken(user);
        RefreshToken refreshTokenResponse= refreshTokenService.createRefreshToken(user);
        String refreshToken=refreshTokenResponse.getToken();
        authResponse.setAccessToken(accessToken);
        authResponse.setRefreshToken(refreshToken);

        authResponse.setTokenType("Bearer");
        Long expiresIn = jwtProperties.getAccessToken().getExpiration() / 1000;  // Convert ms to seconds
        authResponse.setExpiresIn(expiresIn);

        return authResponse;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        AuthResponse authResponse=new AuthResponse();

        User user=userService.findByEmail(request.getEmail())
                .orElseThrow(()->new RuntimeException("Invalid email or password"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid email or password");
        }

        if(user.isEnabled()==false){
            throw new RuntimeException("Account is disabled");
        }

        UserResponse userResponse=mapToUserResponse(user);
        authResponse.setUserResponse(userResponse);
        authResponse.setAccessToken(jwtService.generateAccessToken(user));
        RefreshToken refreshtoken=refreshTokenService.createRefreshToken(user);
        authResponse.setRefreshToken(refreshtoken.getToken());
        authResponse.setTokenType("Bearer");
        authResponse.setExpiresIn(jwtProperties.getAccessToken().getExpiration()/1000);
        return authResponse;
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        AuthResponse authResponse=new AuthResponse();
        String refreshToken=request.getRefreshToken();

        if(refreshToken==null){
            throw new RuntimeException("Refresh token is empty");
        }
        String userName= jwtService.extractUsername(refreshToken);

        if(jwtService.isTokenExpired(refreshToken)) {
            throw new RuntimeException("Refresh token has expired");
        }

        User user=userService.findByUsername(userName)
                .orElseThrow(()->new RuntimeException("username not present"));

        if(!user.isEnabled()) {
            throw new RuntimeException("User account is disabled");
        }

        if(!jwtService.validateToken(refreshToken,user)){
            throw new RuntimeException("User is not valid");
        }

        String accessToken=jwtService.generateAccessToken(user);

        UserResponse userResponse=mapToUserResponse(user);
        authResponse.setUserResponse(userResponse);

        authResponse.setRefreshToken(refreshToken);
        authResponse.setAccessToken(accessToken);
        authResponse.setTokenType("Bearer");
        authResponse.setExpiresIn(jwtProperties.getAccessToken().getExpiration());
        return authResponse;
    }

    @Override
    public void logout(Long userId) {

    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUserName())
                .email(user.getEmail())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .phoneNumber(user.getPhone())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
