package com.khremin.springfox.service.security;

import com.khremin.springfox.model.security.auth.AuthResponse;
import com.khremin.springfox.model.security.UserPrincipal;
import com.khremin.springfox.model.security.auth.AuthRequest;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public interface SecurityService {
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";

    AuthResponse auth(AuthRequest authRequest);

    UserPrincipal getCurrentPrincipal();

    RSAPublicKey getPublicKey();

    RSAPrivateKey getPrivateKey();

}
