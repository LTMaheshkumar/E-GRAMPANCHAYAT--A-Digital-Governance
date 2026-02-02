package com.egrampanchyat.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

@Service
public class RazorpayService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    /**
     * Create Razorpay Order
     */
    public Order createOrder(double amount) throws RazorpayException {

        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject options = new JSONObject();
        options.put("amount", (int) (amount * 100)); // ₹ → paise
        options.put("currency", "INR");
        options.put("receipt", "receipt_" + System.currentTimeMillis());

        return client.orders.create(options);
    }

    /**
     * Verify Razorpay Signature
     */
    public boolean verifySignature(
            String orderId,
            String paymentId,
            String signature
    ) throws RazorpayException {

        String payload = orderId + "|" + paymentId;

        Utils.verifySignature(payload, signature, keySecret);
        return true; // no exception = valid
    }
}
