package com.egrampanchyat.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.egrampanchyat.entity.Payment;
import com.egrampanchyat.entity.TaxType;
import com.egrampanchyat.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public Payment initiatePayment(String email, TaxType taxType, String method) {

        // ✅ MOCK PAYMENT METHOD VALIDATION
        if (!method.equalsIgnoreCase("UPI")
                && !method.equalsIgnoreCase("CARD")
                && !method.equalsIgnoreCase("NET_BANKING")
                && !method.equalsIgnoreCase("RAZORPAY")) {
            throw new RuntimeException("Invalid payment method");
        }

        Payment payment = new Payment();
        payment.setUserEmail(email);
        payment.setTaxType(taxType);
        payment.setMethod(method.toUpperCase());
        payment.setAmount(calculateAmount(taxType));
        payment.setCreatedAt(LocalDateTime.now());

        // ❌ NO status / gateway / transactionId
        // ❌ kyunki entity me field hi nahi hai

        return paymentRepository.save(payment);
    }

    private double calculateAmount(TaxType taxType) {
        return taxType == TaxType.PROPERTY_TAX ? 1200.0 : 350.0;
    }

    @Override
    public List<Payment> myPayments(String email) {
        return paymentRepository.findByUserEmail(email);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
