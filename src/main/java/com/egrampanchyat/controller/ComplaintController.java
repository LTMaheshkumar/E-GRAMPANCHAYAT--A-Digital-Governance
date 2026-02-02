package com.egrampanchyat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egrampanchyat.dto.ComplaintRequestDto;
import com.egrampanchyat.entity.Complaint;
import com.egrampanchyat.service.ComplaintService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService service;

    @PostMapping
    public ResponseEntity<Complaint> raise(
            @Valid @RequestBody ComplaintRequestDto dto,
            Authentication authentication) {

        return ResponseEntity.ok(
                service.raiseComplaint(authentication.getName(), dto));
    }

    @GetMapping("/my")
    public ResponseEntity<List<Complaint>> myComplaints(
            Authentication authentication) {

        return ResponseEntity.ok(
                service.myComplaints(authentication.getName()));
    }
}
