package com.example.publictransport.repository;

import com.example.publictransport.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByTravelCardUserId(Long userId);
    List<Ticket> findByTravelCardId(Long travelCardId);
}
