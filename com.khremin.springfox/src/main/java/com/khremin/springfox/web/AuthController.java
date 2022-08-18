package com.khremin.springfox.web;

import com.khremin.springfox.model.security.auth.AuthRequest;
import com.khremin.springfox.model.security.auth.AuthResponse;
import com.khremin.springfox.service.security.SecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = "Auth methods")
@Tag(name = "Auth methods")
public class AuthController {

    private final SecurityService securityService;

    @PostMapping(path = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Authorization. Return auth JWT token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "If everything is fine."),
            @ApiResponse(responseCode = "400", description = "If missed request parameters."),
            @ApiResponse(responseCode = "415", description = "If Content-type not application/json."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public AuthResponse auth(@RequestBody @Valid AuthRequest authRequest) {
        return securityService.auth(authRequest);
    }
}
