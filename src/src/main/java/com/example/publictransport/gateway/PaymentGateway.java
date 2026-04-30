package com.example.publictransport.gateway;

import java.math.BigDecimal;

public interface PaymentGateway {
    PaymentResult processPayment(BigDecimal amount);
}
