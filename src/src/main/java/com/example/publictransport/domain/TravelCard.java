package com.example.publictransport.domain;

import com.example.publictransport.enums.CardStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "travel_cards")
public class TravelCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number", unique = true, nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardStatus status;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "travelCard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "travelCard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    @PrePersist
    protected void onCreate() {
        if (cardNumber == null) {
            cardNumber = UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
        }
        if (balance == null) {
            balance = BigDecimal.ZERO;
        }
        if (status == null) {
            status = com.example.publictransport.enums.CardStatus.ACTIVE;
        }
        if (issuedAt == null) {
            issuedAt = LocalDateTime.now();
        }
    }
}
