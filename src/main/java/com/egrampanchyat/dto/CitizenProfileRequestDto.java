package com.egrampanchyat.dto;


import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CitizenProfileRequestDto {

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Ward number is required")
    private String wardNo;

    @Pattern(
        regexp = "^[0-9]{12}$",
        message = "Aadhaar must be 12 digits"
    )
    private String aadhaarNo;

    private LocalDate dob;
    private String gender;
}

