package com.egrampanchyat.service;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.egrampanchyat.dto.CertificateApplyRequestDto;
import com.egrampanchyat.entity.ApplicationStatus;
import com.egrampanchyat.entity.CertificateApplication;
import com.egrampanchyat.entity.CitizenProfile;
import com.egrampanchyat.entity.User;
import com.egrampanchyat.exception.ResourceNotFoundException;
import com.egrampanchyat.repository.CertificateApplicationRepository;
import com.egrampanchyat.repository.CitizenProfileRepository;
import com.egrampanchyat.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificateApplicationServiceImpl
        implements CertificateApplicationService {

    private final UserRepository userRepo;
    private final CitizenProfileRepository citizenRepo;
    private final CertificateApplicationRepository appRepo;

    private static final String UPLOAD_DIR = "uploads/certificates/";

    @Override
    public CertificateApplication apply(
            String email,
            CertificateApplyRequestDto dto,
            MultipartFile identityProof,
            MultipartFile photo) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        CitizenProfile profile = citizenRepo.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Citizen profile not found"));

        String idProofPath = saveFile(identityProof);
        String photoPath = saveFile(photo);

        CertificateApplication application = CertificateApplication.builder()
                .citizenProfile(profile)
                .certificateType(dto.getCertificateType())
                .applicantName(dto.getApplicantName())
                .dateOfBirth(dto.getDateOfBirth())
                .income(dto.getIncome())
                .identityProofPath(idProofPath)
                .photoPath(photoPath)
                .status(ApplicationStatus.PENDING)
                .appliedAt(LocalDateTime.now())
                .build();

        return appRepo.save(application);
    }

    private String saveFile(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;

        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(dir, fileName);
            file.transferTo(dest);
            return dest.getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException("File upload failed");
        }
    }

    @Override
    public List<CertificateApplication> getMyApplications(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        CitizenProfile profile = citizenRepo.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Citizen profile not found"));

        return appRepo.findByCitizenProfile(profile);
    }

    @Override
    public CertificateApplication getApprovedApplication(Long id, String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        CitizenProfile profile = citizenRepo.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Citizen profile not found"));

        CertificateApplication app = appRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        if (!app.getCitizenProfile().getId().equals(profile.getId()))
            throw new RuntimeException("Unauthorized");

        if (app.getStatus() != ApplicationStatus.APPROVED)
            throw new RuntimeException("Certificate not approved");

        return app;
    }

    @Override
    public List<CertificateApplication> getAllApplications() {
        return appRepo.findAll();
    }

    @Override
    public CertificateApplication updateStatus(
            Long applicationId,
            ApplicationStatus status,
            String remarks) {

        CertificateApplication app = appRepo.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        if (app.getStatus() != ApplicationStatus.PENDING)
            throw new RuntimeException("Already processed");

        app.setStatus(status);
        app.setRemarks(remarks);

        if (status == ApplicationStatus.APPROVED) {
            app.setApprovedAt(LocalDateTime.now());
            app.setExpiryDate(LocalDate.now().plusYears(1));
            app.setCertificateRefNo("GP-" + System.currentTimeMillis());
        }

        return appRepo.save(app);
    }
}
