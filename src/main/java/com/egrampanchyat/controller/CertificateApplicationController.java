package com.egrampanchyat.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.egrampanchyat.dto.CertificateApplyRequestDto;
import com.egrampanchyat.entity.CertificateApplication;
import com.egrampanchyat.service.CertificateApplicationService;
import com.egrampanchyat.utils.CertificatePdfGenerator;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificateApplicationController {

    private final CertificateApplicationService service;

    // ================= APPLY CERTIFICATE =================
    @PostMapping(
            value = "/apply",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<CertificateApplication> apply(
            @Valid @RequestPart("data") CertificateApplyRequestDto dto,
            @RequestPart(value = "identityProof", required = false) MultipartFile identityProof,
            @RequestPart(value = "photo", required = false) MultipartFile photo,
            Authentication authentication) {

        return ResponseEntity.ok(
                service.apply(authentication.getName(), dto, identityProof, photo)
        );
    }

    // ================= MY APPLICATIONS =================
    @GetMapping("/my")
    public ResponseEntity<List<CertificateApplication>> my(Authentication auth) {
        return ResponseEntity.ok(
                service.getMyApplications(auth.getName())
        );
    }

    // ================= DOWNLOAD CERTIFICATE =================
    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadCertificate(
            @PathVariable Long id,
            Authentication authentication) {

        try {
            CertificateApplication app =
                    service.getApprovedApplication(id, authentication.getName());

            ByteArrayInputStream pdf =
                    CertificatePdfGenerator.generate(app);

            byte[] pdfBytes = pdf.readAllBytes();

            return ResponseEntity.ok()
                    .header(
                            "Content-Disposition",
                            "attachment; filename=certificate_" + id + ".pdf"
                    )
                    .header("Content-Type", "application/pdf")
                    .body(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace(); // IMPORTANT for debugging
            return ResponseEntity.internalServerError().build();
        }
    }
}
