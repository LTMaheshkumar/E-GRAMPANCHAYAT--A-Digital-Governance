package com.egrampanchyat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egrampanchyat.entity.ApplicationStatus;
import com.egrampanchyat.entity.CertificateApplication;
import com.egrampanchyat.entity.CitizenProfile;

public interface CertificateApplicationRepository
        extends JpaRepository<CertificateApplication, Long> {

    List<CertificateApplication> findByCitizenProfile(CitizenProfile profile);

    // ✅ FOR ADMIN DASHBOARD COUNT
    long countByStatus(ApplicationStatus status);
}
