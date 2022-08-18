package com.khremin.springdoc.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.khremin.springdoc.model.security.UserPrincipal;
import com.khremin.springdoc.service.security.SecurityService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final SecurityService securityService;

    public JWTAuthorizationFilter(AuthenticationManager authManager, SecurityService securityService) {
        super(authManager);
        this.securityService = securityService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(SecurityService.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityService.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        } catch (JWTVerificationException e) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityService.HEADER_STRING);
        if (token != null) {
            DecodedJWT decodedJWT = JWT.require(Algorithm.RSA512(securityService.getPublicKey(), securityService.getPrivateKey()))
                    .build()
                    .verify(token.replace(SecurityService.TOKEN_PREFIX, ""));

            if (decodedJWT != null) {
                UUID id = UUID.fromString(decodedJWT.getSubject());
                String email = decodedJWT.getClaim("email").asString();

                List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));

                return new UsernamePasswordAuthenticationToken(UserPrincipal
                        .builder()
                        .id(id)
                        .email(email)
                        .build(),
                        null, grantedAuthorities);
            }

            return null;
        }

        return null;
    }
}
