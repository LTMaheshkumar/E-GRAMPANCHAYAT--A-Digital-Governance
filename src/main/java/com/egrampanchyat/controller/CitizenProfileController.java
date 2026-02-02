package com.egrampanchyat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.egrampanchyat.dto.CitizenProfileRequestDto;
import com.egrampanchyat.entity.CitizenProfile;
import com.egrampanchyat.service.CitizenProfileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/citizen")
@RequiredArgsConstructor
public class CitizenProfileController {

    private final CitizenProfileService service;

    // ✅ CREATE PROFILE
    @PostMapping("/profile")
    public ResponseEntity<CitizenProfile> createProfile(
            @Valid @RequestBody CitizenProfileRequestDto dto,
            Authentication authentication) {

        String email = authentication.getName();
        return ResponseEntity.ok(service.createProfile(email, dto));
    }

    // ✅ GET MY PROFILE
    @GetMapping("/profile")
    public ResponseEntity<CitizenProfile> getProfile(Authentication authentication) {

        String email = authentication.getName();
        return ResponseEntity.ok(service.getMyProfile(email));
    }

    // ✅ UPDATE PROFILE (🔥 THIS WAS MISSING)
    @PutMapping("/profile")
    public ResponseEntity<CitizenProfile> updateProfile(
            @Valid @RequestBody CitizenProfileRequestDto dto,
            Authentication authentication) {

        String email = authentication.getName();
        return ResponseEntity.ok(service.updateProfile(email, dto));
    }
}
