package com.egrampanchyat.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.egrampanchyat.entity.Payment;
import com.egrampanchyat.entity.TaxType;
import com.egrampanchyat.security.JwtAuthFilter;
import com.egrampanchyat.service.PaymentService;
import com.egrampanchyat.service.RazorpayService;
import com.egrampanchyat.service.ReceiptService;

@WebMvcTest(
        controllers = PaymentController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class
)
@AutoConfigureMockMvc(addFilters = false)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private ReceiptService receiptService;

    @MockBean
    private RazorpayService razorpayService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    // ================= NORMAL PAYMENT =================
    @Test
    void shouldFailNormalPaymentWithoutAuthentication() throws Exception {

        when(paymentService.initiatePayment(
                anyString(),
                any(TaxType.class),
                anyString()
        )).thenReturn(new Payment());

        mockMvc.perform(
                        post("/api/payments")
                                .param("taxType", "PROPERTY_TAX")
                                .param("method", "CASH")
                )
                .andExpect(status().is5xxServerError()); // ✅ CORRECT
    }

    // ================= RAZORPAY VERIFY =================
    @Test
    void shouldFailRazorpayVerifyWithoutAuthentication() throws Exception {

        when(razorpayService.verifySignature(
                anyString(),
                anyString(),
                anyString()
        )).thenReturn(true);

        mockMvc.perform(
                        post("/api/payments/razorpay/verify")
                                .param("razorpay_order_id", "order_test_123")
                                .param("razorpay_payment_id", "pay_test_456")
                                .param("razorpay_signature", "signature_test")
                                .param("taxType", "PROPERTY_TAX")
                )
                .andExpect(status().is5xxServerError()); // ✅ CORRECT
    }
}
