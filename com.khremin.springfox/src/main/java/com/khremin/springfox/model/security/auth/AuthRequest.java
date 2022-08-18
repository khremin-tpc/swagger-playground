package com.khremin.springfox.model.security.auth;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;

@Value
@Builder
public class AuthRequest {
    @NotEmpty
    String email;

    @NotEmpty
    //@Size(min = 10, max = 64)
    String password;
}
