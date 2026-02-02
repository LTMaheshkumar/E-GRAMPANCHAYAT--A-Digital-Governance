package com.egrampanchyat.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egrampanchyat.entity.Role;
import com.egrampanchyat.entity.User;
import com.egrampanchyat.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user-test")
@RequiredArgsConstructor
public class UserTestController {

    private final UserRepository userRepository;

    @PostMapping
    public User create() {
        User user = User.builder()
                .name("Test Citizen")
                .email("test@gmail.com")
                .password("1234")
                .mobile("9999999999")
                .role(Role.ROLE_CITIZEN)
                .build();

        return userRepository.save(user);
    }
}

