package com.example.publictransport.service;

import com.example.publictransport.domain.Ticket;
import com.example.publictransport.domain.TravelCard;
import com.example.publictransport.domain.Validator;
import com.example.publictransport.dto.request.ScanRequest;
import com.example.publictransport.dto.response.ScanResponse;
import com.example.publictransport.enums.ValidatorStatus;
import com.example.publictransport.repository.TicketRepository;
import com.example.publictransport.repository.TravelCardRepository;
import com.example.publictransport.repository.ValidatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidatorService {
    private final ValidatorRepository validatorRepository;
    private final TravelCardRepository travelCardRepository;
    private final TicketRepository ticketRepository;

    public ScanResponse scanCard(ScanRequest request) {
        Validator validator = validatorRepository.findByValidatorId(request.getValidatorCode())
                .orElseThrow(() -> new IllegalArgumentException("Validator not found"));

        if (validator.getStatus() != ValidatorStatus.ACTIVE) {
            return ScanResponse.builder()
                    .valid(false)
                    .message("Validator is not active")
                    .build();
        }

        TravelCard card = travelCardRepository.findByCardNumber(request.getCardNumber())
                .orElseThrow(() -> new IllegalArgumentException("Travel card not found"));

        if (card.getStatus() != com.example.publictransport.enums.CardStatus.ACTIVE) {
            return ScanResponse.builder()
                    .valid(false)
                    .message("Travel card is not active")
                    .build();
        }

        List<Ticket> validTickets = ticketRepository.findByTravelCardId(card.getId()).stream()
                .filter(Ticket::isValid)
                .toList();

        if (validTickets.isEmpty()) {
            return ScanResponse.builder()
                    .valid(false)
                    .message("No valid ticket found")
                    .build();
        }

        Ticket ticket = validTickets.get(0);
        return ScanResponse.builder()
                .valid(true)
                .message("Ticket is valid")
                .ticketType(ticket.getType())
                .expiryDate(ticket.getExpiryDate())
                .build();
    }
}
