package com.example.publictransport.dto.response;

import com.example.publictransport.enums.TicketType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ScanResponse {
    private boolean valid;
    private String message;
    private TicketType ticketType;
    private LocalDateTime expiryDate;
}
