package com.khremin.springfox.model.security;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class UserPrincipal {
    UUID id;

    String email;
}