package com.egrampanchyat.dto;

import com.egrampanchyat.entity.ComplaintStatus;
import lombok.Data;

@Data
public class ComplaintStatusRequestDto {
    private ComplaintStatus status;
    private String remarks;
}
