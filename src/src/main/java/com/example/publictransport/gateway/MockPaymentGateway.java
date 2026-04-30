package com.example.publictransport.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Component
public class MockPaymentGateway implements PaymentGateway {

    @Override
    public PaymentResult processPayment(BigDecimal amount) {
        log.info("Mock payment gateway: processing payment of {}", amount);

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return new PaymentResult(false, null, "Invalid amount");
        }

        String transactionId = "MOCK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        log.info("Mock payment gateway: payment successful. Transaction ID: {}", transactionId);

        return new PaymentResult(true, transactionId, null);
    }
}
