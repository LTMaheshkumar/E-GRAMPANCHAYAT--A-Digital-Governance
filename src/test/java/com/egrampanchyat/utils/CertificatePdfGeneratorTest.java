package com.egrampanchyat.utils;

import com.egrampanchyat.entity.ApplicationStatus;
import com.egrampanchyat.entity.CertificateApplication;
import com.egrampanchyat.entity.CertificateType;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CertificatePdfGeneratorTest {

    @Test
    void shouldGeneratePdfWhenOptionalFieldsAreNull() {

        // ================= GIVEN =================
        CertificateApplication app = CertificateApplication.builder()
                .id(1L)
                .applicantName("Test Citizen")
                .certificateRefNo("GP-TEST-001")
                .certificateType(CertificateType.BIRTH)   // REQUIRED
                .status(ApplicationStatus.APPROVED)

                // required dates
                .appliedAt(LocalDateTime.now())
                .approvedAt(LocalDateTime.now())
                .expiryDate(LocalDate.now().plusYears(1))

                // OPTIONAL / NULL fields (real bug scenario)
                .dateOfBirth(null)
                .income(null)
                .photoPath(null)
                .identityProofPath(null)
                .remarks(null)

                .build();

        // ================= WHEN =================
        ByteArrayInputStream pdfStream =
                CertificatePdfGenerator.generate(app);

        // ================= THEN =================
        assertNotNull(pdfStream, "PDF stream should not be null");
        assertTrue(pdfStream.available() > 0,
                "Generated PDF should not be empty");
    }
}
