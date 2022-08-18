package com.khremin.springfox.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
public class CreateUserRequest {
    @NotBlank
    @ApiModelProperty(name = "Email", required = true, example = "email@khremin.com")
    String email;

    @NotBlank
    @ApiModelProperty(name = "Password", required = true, example = "long-long-password")
    String password;

    @ApiModelProperty(name = "First name", example = "John")
    String firstName;

    @ApiModelProperty(name = "Last name", example = "Doe")
    String lastName;
}
