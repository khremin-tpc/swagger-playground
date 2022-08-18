package com.khremin.springdoc.service.user;

import com.khremin.springdoc.model.security.auth.AuthRequest;
import com.khremin.springdoc.model.user.CreateUserRequest;
import com.khremin.springdoc.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll(Integer limit);

    Optional<User> auth(AuthRequest request);

    User create(CreateUserRequest request);
}
