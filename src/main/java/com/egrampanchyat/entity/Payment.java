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
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 Logged-in user email
    @Column(nullable = false)
    private String userEmail;

    // 🔹 PROPERTY_TAX / WATER_TAX
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaxType taxType;

    // 🔹 UPI / CARD / NET_BANKING (ACTUAL FIELD)
    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private Double amount;
    
 // add these fields

    private String gateway;        // RAZORPAY / MOCK
    private String transactionId;  // pay_xxx
    private String status;         // SUCCESS / FAILED


    // 🔹 Payment timestamp (ACTUAL FIELD)
    @Column(nullable = false)
    private LocalDateTime paidAt;
    
    
    private LocalDate expiryDate;


    /* =======================
       🔥 COMPATIBILITY METHODS
       ======================= */

    // 👉 for PaymentServiceImpl (method)
    public String getMethod() {
        return this.paymentMethod;
    }

    public void setMethod(String method) {
        this.paymentMethod = method;
    }

    // 👉 for Receipt / Service (createdAt)
    public LocalDateTime getCreatedAt() {
        return this.paidAt;
    }

    public void setCreatedAt(LocalDateTime time) {
        this.paidAt = time;
    }
}
