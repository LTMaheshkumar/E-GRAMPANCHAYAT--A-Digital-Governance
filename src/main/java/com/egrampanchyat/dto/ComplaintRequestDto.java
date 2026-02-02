package com.egrampanchyat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ComplaintRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;
}
