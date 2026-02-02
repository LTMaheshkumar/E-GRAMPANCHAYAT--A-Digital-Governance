package com.egrampanchyat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {

    private Long userId;
    private String name;
    private String email;
    private String role;
    private String token;   // ✅ JWT token added
    private String message;
}
