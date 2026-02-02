package com.egrampanchyat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egrampanchyat.dto.LoginRequestDto;
import com.egrampanchyat.dto.LoginResponseDto;
import com.egrampanchyat.dto.RegisterRequestDto;
import com.egrampanchyat.entity.User;
import com.egrampanchyat.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequestDto dto) {
        User user = userService.register(dto);
        return ResponseEntity.ok(user);
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto dto) {

        LoginResponseDto response = userService.login(dto);
        return ResponseEntity.ok(response);
    }
}

