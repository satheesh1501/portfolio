package com.satheesh.portfolio.repository;

import com.satheesh.portfolio.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for ContactMessage entities.
 *
 * Spring Data auto-generates SQL implementations for all method names at startup.
 * No @Query needed — method names follow Spring Data naming conventions.
 */
@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

    /**
     * Finds submissions from the same email after a given time.
     * Used for duplicate submission detection:
     *   e.g. findByEmailAndCreatedAtAfter(email, LocalDateTime.now().minusMinutes(5))
     */
    List<ContactMessage> findByEmailAndCreatedAtAfter(String email, LocalDateTime after);

    /**
     * Counts submissions within a date range.
     * Used for analytics: daily/weekly submission metrics.
     */
    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
