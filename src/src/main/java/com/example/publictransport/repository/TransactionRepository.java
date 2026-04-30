package com.example.publictransport.repository;

import com.example.publictransport.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByTravelCardUserId(Long userId);
    List<Transaction> findByTravelCardId(Long travelCardId);
}
