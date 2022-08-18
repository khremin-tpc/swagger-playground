package com.khremin.springdoc.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
public class CreateUserRequest {
    @NotBlank
    @Schema(name = "Email", required = true, example = "email@khremin.com")
    String email;

    @NotBlank
    @Schema(name = "Password", required = true, example = "long-long-password")
    String password;

    @Schema(name = "First name", example = "John")
    String firstName;

    @Schema(name = "Last name", example = "Doe")
    String lastName;
}
