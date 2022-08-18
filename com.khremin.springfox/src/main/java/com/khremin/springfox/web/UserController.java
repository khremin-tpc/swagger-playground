package com.khremin.springfox.web;

import com.khremin.springfox.model.user.CreateUserRequest;
import com.khremin.springfox.model.user.User;
import com.khremin.springfox.service.user.UserService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "Users")
@Tag(name = "Users")

public class UserController {

    private final UserService userService;

    @GetMapping(path = "/users")
    @ApiOperation("Get all user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "If everything is fine."),
            @ApiResponse(responseCode = "400", description = "If missed request parameters."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public List<User> getAll(@RequestParam(name = "limit", required = false) @ApiParam("Max number of users in the response") Integer limit) {
        return userService.getAll(limit);
    }

    @PostMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Create user.")
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
