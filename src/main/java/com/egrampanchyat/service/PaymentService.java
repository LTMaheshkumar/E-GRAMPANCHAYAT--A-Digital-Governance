package com.egrampanchyat.service;

import java.util.List;

import com.egrampanchyat.entity.Payment;
import com.egrampanchyat.entity.TaxType;

public interface PaymentService {

    Payment initiatePayment(String email, TaxType taxType, String method);

    List<Payment> myPayments(String email);

    Payment getPaymentById(Long id);

    // 🔥 ADD THIS FOR ADMIN
    List<Payment> getAllPayments();
}
