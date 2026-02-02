package com.egrampanchyat.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egrampanchyat.entity.ApplicationStatus;
import com.egrampanchyat.entity.ComplaintStatus;
import com.egrampanchyat.repository.CertificateApplicationRepository;
import com.egrampanchyat.repository.CitizenProfileRepository;
import com.egrampanchyat.repository.ComplaintRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardSummaryController {

    private final CitizenProfileRepository citizenRepo;
    private final CertificateApplicationRepository certificateRepo;
    private final ComplaintRepository complaintRepo;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Long>> getSummary() {

        Map<String, Long> summary = new HashMap<>();

        summary.put("totalCitizens", citizenRepo.count());
        summary.put(
            "pendingCertificates",
            certificateRepo.countByStatus(ApplicationStatus.PENDING)
        );
        summary.put(
            "pendingComplaints",
            complaintRepo.countByStatus(ComplaintStatus.PENDING)
        );
        summary.put(
            "resolvedComplaints",
            complaintRepo.countByStatus(ComplaintStatus.RESOLVED)
        );

        return ResponseEntity.ok(summary);
    }
}
