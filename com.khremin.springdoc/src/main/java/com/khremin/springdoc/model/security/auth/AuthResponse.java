package com.khremin.springdoc.model.security.auth;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthResponse {
    String token;

    String tokenType;

    String tokenPrefix;
}
