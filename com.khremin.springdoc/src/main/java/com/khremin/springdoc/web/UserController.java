package com.khremin.springdoc.web;

import com.khremin.springdoc.model.user.CreateUserRequest;
import com.khremin.springdoc.model.user.User;
import com.khremin.springdoc.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Users")
@SecurityRequirement(name = "Bearer token")
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "If everything is fine."),
            @ApiResponse(responseCode = "400", description = "If missed request parameters."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public List<User> getAll(@RequestParam(name = "limit", required = false) @Parameter(description = "Max number of users in the response") Integer limit) {
        return userService.getAll(limit);
    }

    @PostMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Create user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "If everything is fine."),
            @ApiResponse(responseCode = "400", description = "If missed request parameters."),
            @ApiResponse(responseCode = "415", description = "If Content-type not application/json."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public User auth(@RequestBody @Valid CreateUserRequest request) {
        return userService.create(request);
    }
}
