package com.example.publictransport.service;

import com.example.publictransport.domain.Ticket;
import com.example.publictransport.domain.Transaction;
import com.example.publictransport.domain.TravelCard;
import com.example.publictransport.dto.request.PurchaseTicketRequest;
import com.example.publictransport.dto.response.TicketResponse;
import com.example.publictransport.enums.Status;
import com.example.publictransport.enums.TicketType;
import com.example.publictransport.repository.TicketRepository;
import com.example.publictransport.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TravelCardService travelCardService;
    private final TransactionRepository transactionRepository;

    private static final BigDecimal SINGLE_PRICE = new BigDecimal("2.50");
    private static final BigDecimal DAILY_PRICE = new BigDecimal("6.00");
    private static final BigDecimal MONTHLY_PRICE = new BigDecimal("45.00");

    public List<TicketResponse> getMyTickets(Long userId) {
        Long cardId = travelCardService.getCardByUserId(userId).getId();
        return ticketRepository.findByTravelCardId(cardId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public TicketResponse purchaseTicket(Long userId, PurchaseTicketRequest request) {
        TravelCard card = travelCardService.getCardByUserId(userId);
        BigDecimal price = getPriceForType(request.getType());

        if (!travelCardService.hasSufficientBalance(card.getId(), price)) {
            createFailedTransaction(card, price, Status.INSUFFICIENT_FUNDS);
            throw new IllegalStateException("Insufficient balance");
        }

        travelCardService.deductBalance(card.getId(), price);

        Ticket ticket = new Ticket();
        ticket.setType(request.getType());
        ticket.setPrice(price);
        ticket.setTravelCard(card);
        ticket.setIssueDate(LocalDateTime.now());
        ticket.setExpiryDate(calculateExpiryDate(LocalDateTime.now(), request.getType()));
        ticket = ticketRepository.save(ticket);

        createSuccessTransaction(card, price, Status.APPROVED, com.example.publictransport.enums.TransactionType.PURCHASE);

        return toResponse(ticket);
    }

    public TicketResponse validateTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
        return toResponse(ticket);
    }

    private BigDecimal getPriceForType(TicketType type) {
        return switch (type) {
            case SINGLE -> SINGLE_PRICE;
            case DAILY -> DAILY_PRICE;
            case MONTHLY -> MONTHLY_PRICE;
        };
    }

    private LocalDateTime calculateExpiryDate(LocalDateTime issueDate, TicketType type) {
        return switch (type) {
            case SINGLE, DAILY -> issueDate.plusDays(1);
            case MONTHLY -> issueDate.plusDays(30);
        };
    }

    private TicketResponse toResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .type(ticket.getType())
                .price(ticket.getPrice())
                .issueDate(ticket.getIssueDate())
                .expiryDate(ticket.getExpiryDate())
                .valid(ticket.isValid())
                .build();
    }

    private void createSuccessTransaction(TravelCard card, BigDecimal amount, Status status, com.example.publictransport.enums.TransactionType type) {
        Transaction tx = new Transaction();
        tx.setTravelCard(card);
        tx.setAmount(amount);
        tx.setType(type);
        tx.setStatus(status);
        tx.setTimestamp(LocalDateTime.now());
        transactionRepository.save(tx);
    }

    private void createFailedTransaction(TravelCard card, BigDecimal amount, Status status) {
        Transaction tx = new Transaction();
        tx.setTravelCard(card);
        tx.setAmount(amount);
        tx.setType(com.example.publictransport.enums.TransactionType.PURCHASE);
        tx.setStatus(status);
        tx.setTimestamp(LocalDateTime.now());
        transactionRepository.save(tx);
    }
}
