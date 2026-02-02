package com.egrampanchyat.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.egrampanchyat.dto.CertificateApplyRequestDto;
import com.egrampanchyat.entity.ApplicationStatus;
import com.egrampanchyat.entity.CertificateApplication;

public interface CertificateApplicationService {

    // ================== CITIZEN ==================

    CertificateApplication apply(
            String email,
            CertificateApplyRequestDto dto,
            MultipartFile identityProof,
            MultipartFile photo
    );

    List<CertificateApplication> getMyApplications(String email);

    CertificateApplication getApprovedApplication(Long id, String email);

    // ================== ADMIN ==================

    List<CertificateApplication> getAllApplications();

    CertificateApplication updateStatus(
            Long applicationId,
            ApplicationStatus status,
            String remarks
    );
}
