package com.khremin.springdoc.web;

import com.khremin.springdoc.model.security.auth.AuthRequest;
import com.khremin.springdoc.model.security.auth.AuthResponse;
import com.khremin.springdoc.service.security.SecurityService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Tag(name = "Auth methods")
public class AuthController {

    private final SecurityService securityService;

    @PostMapping(path = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "If everything is fine.", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "If missed request parameters."),
            @ApiResponse(responseCode = "415", description = "If Content-type not application/json."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public AuthResponse auth(@RequestBody @Valid AuthRequest authRequest) {
        return securityService.auth(authRequest);
    }
}
