package com.khremin.springfox.service.security.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.khremin.springfox.config.properties.SecurityProperties;
import com.khremin.springfox.exception.UnauthorizedException;
import com.khremin.springfox.model.security.UserPrincipal;
import com.khremin.springfox.model.security.auth.AuthRequest;
import com.khremin.springfox.model.security.auth.AuthResponse;
import com.khremin.springfox.model.user.User;
import com.khremin.springfox.service.security.SecurityService;
import com.khremin.springfox.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

@Service
@Slf4j
public class SecurityServiceImpl implements SecurityService {
    private static final long EXPIRATION_TIME = 864_000_000;

    private static final String KEY_ALIAS = "auth_keys";

    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    private final UserService userService;

    public SecurityServiceImpl(SecurityProperties securityProperties, UserService userService) {
        try {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            InputStream ins = resourceLoader.getResource(securityProperties.getKeyStorePath()).getInputStream();

            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(ins, securityProperties.getKeyStorePassword().toCharArray());   // Keystore password
            KeyStore.PasswordProtection keyPassword = new KeyStore.PasswordProtection(securityProperties.getKeyStorePassword().toCharArray()); // Key password

            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(KEY_ALIAS, keyPassword);

            java.security.cert.Certificate cert = keyStore.getCertificate(KEY_ALIAS);

            this.publicKey = (RSAPublicKey) cert.getPublicKey();
            this.privateKey = (RSAPrivateKey) privateKeyEntry.getPrivateKey();
            this.userService = userService;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public AuthResponse auth(AuthRequest authRequest) {
        User user = userService.auth(authRequest)
                .orElseThrow(UnauthorizedException::new);

        String token = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("email", user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.RSA512(publicKey, privateKey));

        return AuthResponse.builder()
                .token(token)
                .tokenPrefix("Bearer ")
                .tokenType("JWT")
                .build();
    }

    @Override
    public UserPrincipal getCurrentPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException();
        } else {
            return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
    }

    @Override
    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    @Override
    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }
}
