package com.khremin.springdoc.model.security.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Value
@Builder
public class AuthRequest {
    @NotEmpty
    @Schema(description = "An email for authorization")
    String email;

    @NotEmpty
    @Size(min = 4, max = 64)
    String password;
}
