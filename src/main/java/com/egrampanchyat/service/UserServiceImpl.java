package com.egrampanchyat.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.egrampanchyat.dto.LoginRequestDto;
import com.egrampanchyat.dto.LoginResponseDto;
import com.egrampanchyat.dto.RegisterRequestDto;
import com.egrampanchyat.entity.Role;
import com.egrampanchyat.entity.User;
import com.egrampanchyat.exception.ResourceAlreadyExistsException;
import com.egrampanchyat.exception.ResourceNotFoundException;
import com.egrampanchyat.repository.UserRepository;
import com.egrampanchyat.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;   // ✅ JWT UTIL injected

    @Override
    public User register(RegisterRequestDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered");
        }

        if (userRepository.existsByMobile(dto.getMobile())) {
            throw new ResourceAlreadyExistsException("Mobile already registered");
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .mobile(dto.getMobile())
                .role(Role.ROLE_CITIZEN)
                .active(true)
                .build();

        return userRepository.save(user);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Invalid email or password");
        }

        if (!user.isActive()) {
            throw new RuntimeException("User account is inactive");
        }

        // ✅ JWT TOKEN GENERATION
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return LoginResponseDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .token(token)                    // ✅ token added
                .message("Login successful")
                .build();
    }
}
