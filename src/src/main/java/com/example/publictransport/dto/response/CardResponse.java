package com.example.publictransport.dto.response;

import com.example.publictransport.enums.CardStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CardResponse {
    private Long id;
    private String cardNumber;
    private BigDecimal balance;
    private CardStatus status;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
}
