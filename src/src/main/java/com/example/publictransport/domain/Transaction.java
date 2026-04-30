package com.example.publictransport.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private com.example.publictransport.enums.TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private com.example.publictransport.enums.Status status;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "gateway_transaction_id")
    private String gatewayTransactionId;

    @ManyToOne
    @JoinColumn(name = "travel_card_id", nullable = false)
    private TravelCard travelCard;

    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }
}

