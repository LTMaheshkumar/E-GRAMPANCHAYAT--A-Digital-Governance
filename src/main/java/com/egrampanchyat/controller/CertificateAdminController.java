package com.egrampanchyat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egrampanchyat.dto.CertificateApprovalRequestDto;
import com.egrampanchyat.entity.CertificateApplication;
import com.egrampanchyat.service.CertificateApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/certificates")
@RequiredArgsConstructor
public class CertificateAdminController {

    private final CertificateApplicationService service;

    // VIEW ALL APPLICATIONS
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CLERK')")
    public ResponseEntity<List<CertificateApplication>> getAll() {
        return ResponseEntity.ok(service.getAllApplications());
    }

    // APPROVE / REJECT APPLICATION
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CLERK')")
    public ResponseEntity<CertificateApplication> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody CertificateApprovalRequestDto dto) {

        return ResponseEntity.ok(
                service.updateStatus(id, dto.getStatus(), dto.getRemarks()));
    }
}
