package com.egrampanchyat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egrampanchyat.entity.CitizenProfile;
import com.egrampanchyat.repository.CitizenProfileRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/citizens")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminCitizenController {

    private final CitizenProfileRepository citizenProfileRepository;

    @GetMapping
    public ResponseEntity<List<CitizenProfile>> getAllCitizens() {
        return ResponseEntity.ok(
                citizenProfileRepository.findAll()
        );
    }
}
