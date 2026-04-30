package com.example.publictransport.gateway;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResult {
    private boolean success;
    private String gatewayTransactionId;
    private String errorMessage;
}
