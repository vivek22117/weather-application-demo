package com.weather.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse implements Serializable {
    private String authenticationToken;
    private Instant expiresAt;
    private String username;
    private Boolean isAuthenticated;
}
