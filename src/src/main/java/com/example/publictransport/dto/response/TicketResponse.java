package com.example.publictransport.dto.response;

import com.example.publictransport.enums.TicketType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TicketResponse {
    private Long id;
    private TicketType type;
    private BigDecimal price;
    private LocalDateTime issueDate;
    private LocalDateTime expiryDate;
    private boolean valid;
}
