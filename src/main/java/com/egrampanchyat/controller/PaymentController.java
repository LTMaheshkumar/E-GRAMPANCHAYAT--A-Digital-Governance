package com.egrampanchyat.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egrampanchyat.dto.RazorpayOrderResponse;
import com.egrampanchyat.entity.Payment;
import com.egrampanchyat.entity.TaxType;
import com.egrampanchyat.service.PaymentService;
import com.egrampanchyat.service.RazorpayService;
import com.egrampanchyat.service.ReceiptService;
import com.razorpay.Order;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final ReceiptService receiptService;
    private final RazorpayService razorpayService;

    // ================= NORMAL PAYMENT =================
    @PostMapping
    public ResponseEntity<Payment> pay(
            @RequestParam TaxType taxType,
            @RequestParam String method,
            Authentication authentication) {

        return ResponseEntity.ok(
                paymentService.initiatePayment(
                        authentication.getName(),
                        taxType,
                        method
                )
        );
    }

    // ================= MY PAYMENTS =================
    @GetMapping("/my")
    public ResponseEntity<List<Payment>> myPayments(Authentication authentication) {
        return ResponseEntity.ok(
                paymentService.myPayments(authentication.getName())
        );
    }

    // ================= RECEIPT =================
    @GetMapping("/{id}/receipt")
    public ResponseEntity<byte[]> downloadReceipt(@PathVariable Long id)
            throws Exception {

        Payment payment = paymentService.getPaymentById(id);
        byte[] pdf = receiptService.generateReceipt(payment);

        return ResponseEntity.ok()
                .header(
                        "Content-Disposition",
                        "attachment; filename=payment-receipt-" + id + ".pdf"
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    // ================= RAZORPAY REAL FLOW =================

    // STEP 1: CREATE RAZORPAY ORDER
    @PostMapping("/razorpay/order")
    public ResponseEntity<RazorpayOrderResponse> createRazorpayOrder(
            @RequestParam TaxType taxType) throws Exception {

        double amount = taxType == TaxType.PROPERTY_TAX ? 1200 : 300;

        Order order = razorpayService.createOrder(amount);

        return ResponseEntity.ok(
                new RazorpayOrderResponse(
                        order.get("id"),
                        amount,
                        order.get("currency")
                )
        );
    }

    // STEP 2: VERIFY PAYMENT & SAVE
    @PostMapping("/razorpay/verify")
    public ResponseEntity<Payment> verifyRazorpayPayment(
            @RequestParam String razorpay_order_id,
            @RequestParam String razorpay_payment_id,
            @RequestParam String razorpay_signature,
            @RequestParam TaxType taxType,
            Authentication authentication) throws Exception {

        // 🔐 Verify signature (security)
        razorpayService.verifySignature(
                razorpay_order_id,
                razorpay_payment_id,
                razorpay_signature
        );

        // 💾 Save payment in DB
        Payment payment = paymentService.initiatePayment(
                authentication.getName(),
                taxType,
                "RAZORPAY"
        );

        return ResponseEntity.ok(payment);
    }
}
