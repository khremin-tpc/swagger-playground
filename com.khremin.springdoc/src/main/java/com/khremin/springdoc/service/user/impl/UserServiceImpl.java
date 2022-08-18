package com.khremin.springdoc.service.user.impl;

import com.khremin.springdoc.model.security.auth.AuthRequest;
import com.khremin.springdoc.model.user.CreateUserRequest;
import com.khremin.springdoc.model.user.User;
import com.khremin.springdoc.persistence.user.entity.UserEntity;
import com.khremin.springdoc.persistence.user.repository.UserRepository;
import com.khremin.springdoc.service.user.UserService;
import com.khremin.springdoc.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConversionService conversionService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void init() {
        userRepository.save(UserEntity.builder()
                .id(UUID.randomUUID())
                .email("root@khremin.com")
                .firstName("First")
                .lastName("Last")
                .password(passwordEncoder.encode("q1w2e3r4"))
                .build());
    }

    @Override
    public List<User> getAll(Integer limit) {
        Pageable pageable;

        if (limit == null) {
            pageable = Pageable.unpaged();
        } else {
            pageable = Pageable.ofSize(limit);
        }

        return userRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(user -> conversionService.convert(user, User.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> auth(AuthRequest request) {
        Optional<UserEntity> user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            return Optional.empty();
        }

        if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            return Optional.empty();
        }

        return Optional.ofNullable(
                conversionService.convert(user.get(), User.class)
        );
    }

    @Override
    @Transactional
    public User create(CreateUserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email should be unique.");
        }

        UserEntity user = userRepository.save(UserEntity.builder()
                .id(UUID.randomUUID())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build());


        return conversionService.convert(user, User.class);
    }
}
