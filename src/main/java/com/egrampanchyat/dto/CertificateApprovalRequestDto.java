package com.egrampanchyat.dto;

import com.egrampanchyat.entity.ApplicationStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CertificateApprovalRequestDto {

    @NotNull(message = "Status is required")
    private ApplicationStatus status; // APPROVED / REJECTED

    private String remarks;
}
