package com.egrampanchyat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egrampanchyat.entity.Payment;
import com.egrampanchyat.entity.TaxType;
import com.egrampanchyat.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/dashboard/payments")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminPaymentDashboardController {

    private final PaymentRepository paymentRepo;

    @GetMapping("/summary")
    public ResponseEntity<?> summary() {
        return ResponseEntity.ok(
            new Object() {
                public final long totalPayments = paymentRepo.count();
                public final long propertyTaxCount =
                        paymentRepo.countByTaxType(TaxType.PROPERTY_TAX);
                public final long waterTaxCount =
                        paymentRepo.countByTaxType(TaxType.WATER_BILL);
            }
        );
    }

    @GetMapping
    public List<Payment> allPayments() {
        return paymentRepo.findAll();
    }
}
