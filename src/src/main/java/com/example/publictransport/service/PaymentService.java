package com.example.publictransport.service;

import com.example.publictransport.domain.Transaction;
import com.example.publictransport.domain.TravelCard;
import com.example.publictransport.dto.request.TopUpRequest;
import com.example.publictransport.dto.response.TransactionResponse;
import com.example.publictransport.enums.Status;
import com.example.publictransport.enums.TransactionType;
import com.example.publictransport.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final TravelCardService travelCardService;
    private final TransactionRepository transactionRepository;

    @Transactional
    public void topUp(Long userId, TopUpRequest request) {
        TravelCard card = travelCardService.getCardByUserId(userId);

        if (card.getStatus() != com.example.publictransport.enums.CardStatus.ACTIVE) {
            createTransaction(card, request.getAmount(), Status.INVALID_TICKET, TransactionType.TOPUP);
            throw new IllegalStateException("Travel card is not active");
        }

        travelCardService.topUp(card.getId(), request.getAmount());

        createTransaction(card, request.getAmount(), Status.APPROVED, TransactionType.TOPUP);
    }

    public List<TransactionResponse> getTransactionHistory(Long userId) {
        Long cardId = travelCardService.getCardByUserId(userId).getId();
        return transactionRepository.findByTravelCardId(cardId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private TransactionResponse toResponse(Transaction tx) {
        return TransactionResponse.builder()
                .id(tx.getId())
                .amount(tx.getAmount())
                .type(tx.getType())
                .status(tx.getStatus())
                .timestamp(tx.getTimestamp())
                .build();
    }

    private void createTransaction(TravelCard card, java.math.BigDecimal amount, Status status, TransactionType type) {
        Transaction tx = new Transaction();
        tx.setTravelCard(card);
        tx.setAmount(amount);
        tx.setType(type);
        tx.setStatus(status);
        tx.setTimestamp(java.time.LocalDateTime.now());
        transactionRepository.save(tx);
    }
}
