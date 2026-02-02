package com.egrampanchyat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RazorpayOrderResponse {

    private String orderId;
    private Double amount;
    private String currency;
}
