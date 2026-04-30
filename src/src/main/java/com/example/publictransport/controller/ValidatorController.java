package com.example.publictransport.controller;

import com.example.publictransport.dto.request.ScanRequest;
import com.example.publictransport.dto.response.ScanResponse;
import com.example.publictransport.service.ValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validate")
@RequiredArgsConstructor
public class ValidatorController {
    private final ValidatorService validatorService;

    @PostMapping("/scan")
    public ResponseEntity<ScanResponse> scanCard(@RequestBody ScanRequest request) {
        try {
            ScanResponse response = validatorService.scanCard(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ScanResponse.builder()
                            .valid(false)
                            .message(e.getMessage())
                            .build());
        }
    }

//    @GetMapping("/validators")
//    public ResponseEntity<String> listValidators() {
//        return ResponseEntity.ok("Validators endpoint - open access");
//    }
}
