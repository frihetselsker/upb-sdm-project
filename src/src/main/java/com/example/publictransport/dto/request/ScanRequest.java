package com.example.publictransport.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ScanRequest {
    @NotBlank
    private String cardNumber;

    @NotBlank
    private String validatorCode;
}
