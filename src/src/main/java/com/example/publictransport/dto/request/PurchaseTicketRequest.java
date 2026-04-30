package com.example.publictransport.dto.request;

import com.example.publictransport.enums.TicketType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PurchaseTicketRequest {
    @NotNull
    private TicketType type;
}
