package com.egrampanchyat.service;

import com.egrampanchyat.entity.Payment;

public interface ReceiptService {

    byte[] generateReceipt(Payment payment) throws Exception;

}
