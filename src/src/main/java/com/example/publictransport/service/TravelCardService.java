package com.example.publictransport.service;

import com.example.publictransport.domain.TravelCard;
import com.example.publictransport.domain.User;
import com.example.publictransport.enums.CardStatus;
import com.example.publictransport.repository.TravelCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TravelCardService {
    private final TravelCardRepository travelCardRepository;

    @Transactional
    public TravelCard createCard(User user) {
        TravelCard card = new TravelCard();
        card.setUser(user);
        card.setBalance(java.math.BigDecimal.ZERO);
        card.setStatus(CardStatus.ACTIVE);
        card.setIssuedAt(LocalDateTime.now());
        return travelCardRepository.save(card);
    }

    public TravelCard getCardByUserId(Long userId) {
        return travelCardRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Travel card not found for user"));
    }

    @Transactional
    public TravelCard topUp(Long cardId, java.math.BigDecimal amount) {
        TravelCard card = travelCardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Travel card not found"));

        if (card.getStatus() != CardStatus.ACTIVE) {
            throw new IllegalStateException("Travel card is not active");
        }

        card.setBalance(card.getBalance().add(amount));
        return travelCardRepository.save(card);
    }

    public boolean hasSufficientBalance(Long cardId, java.math.BigDecimal amount) {
        TravelCard card = travelCardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Travel card not found"));
        return card.getBalance().compareTo(amount) >= 0;
    }

    public void deductBalance(Long cardId, java.math.BigDecimal amount) {
        TravelCard card = travelCardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Travel card not found"));

        if (card.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }

        card.setBalance(card.getBalance().subtract(amount));
        travelCardRepository.save(card);
    }
}
