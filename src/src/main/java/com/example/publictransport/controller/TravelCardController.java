package com.example.publictransport.controller;

import com.example.publictransport.dto.request.TopUpRequest;
import com.example.publictransport.dto.response.CardResponse;
import com.example.publictransport.service.PaymentService;
import com.example.publictransport.service.TravelCardService;
import com.example.publictransport.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class TravelCardController {
    private final TravelCardService travelCardService;
    private final PaymentService paymentService;
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<CardResponse> getMyCard(Authentication authentication) {
        Long userId = getUserId(authentication);
        CardResponse response = toResponse(travelCardService.getCardByUserId(userId));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/topup")
    public ResponseEntity<CardResponse> topUp(@RequestBody TopUpRequest request, Authentication authentication) {
        Long userId = getUserId(authentication);
        paymentService.topUp(userId, request);
        CardResponse response = toResponse(travelCardService.getCardByUserId(userId));
        return ResponseEntity.ok(response);
    }

    private CardResponse toResponse(com.example.publictransport.domain.TravelCard card) {
        return CardResponse.builder()
                .id(card.getId())
                .cardNumber(card.getCardNumber())
                .balance(card.getBalance())
                .status(card.getStatus())
                .issuedAt(card.getIssuedAt())
                .expiresAt(card.getExpiresAt())
                .build();
    }

    private Long getUserId(Authentication authentication) {
        String username = authentication.getName();
        return userService.findByUsername(username)
                .map(com.example.publictransport.domain.User::getId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
