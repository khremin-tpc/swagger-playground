package com.khremin.springdoc.service.security;

import com.khremin.springdoc.model.security.UserPrincipal;
import com.khremin.springdoc.model.security.auth.AuthRequest;
import com.khremin.springdoc.model.security.auth.AuthResponse;

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
