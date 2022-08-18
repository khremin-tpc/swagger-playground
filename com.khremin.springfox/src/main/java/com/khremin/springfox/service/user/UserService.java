package com.khremin.springfox.service.user;

import com.khremin.springfox.model.security.auth.AuthRequest;
import com.khremin.springfox.model.user.CreateUserRequest;
import com.khremin.springfox.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll(Integer limit);

    Optional<User> auth(AuthRequest request);

    User create(CreateUserRequest request);
}
