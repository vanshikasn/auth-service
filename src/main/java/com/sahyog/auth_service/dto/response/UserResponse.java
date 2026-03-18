package com.sahyog.auth_service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String username;

    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String  role;

    private Boolean enabled;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
