package com.example.publictransport.repository;

import com.example.publictransport.domain.TravelCard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TravelCardRepository extends JpaRepository<TravelCard, Long> {
    Optional<TravelCard> findByUserId(Long userId);
    Optional<TravelCard> findByCardNumber(String cardNumber);
}
