package com.egrampanchyat.dto;

import java.time.LocalDate;

import com.egrampanchyat.entity.CertificateType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CertificateApplyRequestDto {

    @NotNull(message = "Certificate type is required")
    private CertificateType certificateType;

    @NotBlank(message = "Applicant name is required")
    private String applicantName;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    // Only for INCOME certificate
    private Double income;
}
