package com.egrampanchyat.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "certificate_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "citizen_id", nullable = false)
    private CitizenProfile citizenProfile;

    @Enumerated(EnumType.STRING)
    private CertificateType certificateType;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private String remarks;

    private LocalDateTime appliedAt;

    // ====== NEW FIELDS (FORM DATA) ======

    @Column(nullable = false)
    private String applicantName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    // only for INCOME certificate (nullable)
    private Double income;

    // optional uploads
    private String identityProofPath;
    private String photoPath;

    // ====== CERTIFICATE METADATA ======
    private LocalDateTime approvedAt;
    private LocalDate expiryDate;

    @Column(unique = true)
    private String certificateRefNo;
}
