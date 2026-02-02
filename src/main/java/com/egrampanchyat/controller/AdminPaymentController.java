package com.egrampanchyat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egrampanchyat.entity.Payment;
import com.egrampanchyat.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/payments")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminPaymentController {

    private final PaymentService service;

    @GetMapping
    public ResponseEntity<List<Payment>> all() {
        return ResponseEntity.ok(service.getAllPayments());
    }
}
