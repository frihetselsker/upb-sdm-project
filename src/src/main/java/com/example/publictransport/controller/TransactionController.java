package com.example.publictransport.controller;

import com.example.publictransport.dto.response.TransactionResponse;
import com.example.publictransport.service.PaymentService;
import com.example.publictransport.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final PaymentService paymentService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getTransactionHistory(Authentication authentication) {
        Long userId = getUserId(authentication);
        return ResponseEntity.ok(paymentService.getTransactionHistory(userId));
    }

    private Long getUserId(Authentication authentication) {
        String username = authentication.getName();
        return userService.findByUsername(username)
                .map(com.example.publictransport.domain.User::getId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
