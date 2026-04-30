package com.example.publictransport.controller;

import com.example.publictransport.dto.request.PurchaseTicketRequest;
import com.example.publictransport.dto.response.TicketResponse;
import com.example.publictransport.service.TicketService;
import com.example.publictransport.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getMyTickets(Authentication authentication) {
        Long userId = getUserId(authentication);
        return ResponseEntity.ok(ticketService.getMyTickets(userId));
    }

    @PostMapping
    public ResponseEntity<TicketResponse> purchaseTicket(
            @RequestBody PurchaseTicketRequest request,
            Authentication authentication) {
        Long userId = getUserId(authentication);
        return ResponseEntity.ok(ticketService.purchaseTicket(userId, request));
    }

    @GetMapping("/{id}/valid")
    public ResponseEntity<TicketResponse> validateTicket(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.validateTicket(id));
    }

    private Long getUserId(Authentication authentication) {
        String username = authentication.getName();
        return userService.findByEmail(username)
                .map(com.example.publictransport.domain.User::getId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
