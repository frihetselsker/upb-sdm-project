package com.example.publictransport.dto.response;

import com.example.publictransport.enums.Status;
import com.example.publictransport.enums.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {
    private Long id;
    private BigDecimal amount;
    private TransactionType type;
    private Status status;
    private LocalDateTime timestamp;
}
