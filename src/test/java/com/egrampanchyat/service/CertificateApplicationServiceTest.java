package com.egrampanchyat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.egrampanchyat.entity.CertificateApplication;
import com.egrampanchyat.service.CertificateApplicationService;

@SpringBootTest
class CertificateApplicationServiceTest {

    @Autowired
    private CertificateApplicationService service;

    @Test
    void shouldFetchApprovedApplication() {
        Long applicationId = 27L;   // APPROVED record id
        String email = "mahesh@gmail.com"; // same citizen ka email

        CertificateApplication app =
                service.getApprovedApplication(applicationId, email);

        assertNotNull(app);
        assertEquals("APPROVED", app.getStatus().name());
    }
}
