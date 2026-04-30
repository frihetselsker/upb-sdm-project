package com.example.publictransport.domain;

import com.example.publictransport.enums.TicketType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketType type;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "issue_date", nullable = false)
    private LocalDateTime issueDate;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @ManyToOne
    @JoinColumn(name = "travel_card_id", nullable = false)
    private TravelCard travelCard;

    @PrePersist
    protected void onCreate() {
        issueDate = LocalDateTime.now();
        expiryDate = calculateExpiryDate(issueDate, type);
    }

    private LocalDateTime calculateExpiryDate(LocalDateTime issueDate, TicketType type) {
        return switch (type) {
            case SINGLE, DAILY -> issueDate.plusDays(1);
            case MONTHLY -> issueDate.plusDays(30);
        };
    }

    public boolean isValid() {
        return LocalDateTime.now().isBefore(expiryDate);
    }
}
