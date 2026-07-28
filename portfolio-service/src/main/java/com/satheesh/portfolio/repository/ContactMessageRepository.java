package com.satheesh.portfolio.repository;

import com.satheesh.portfolio.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Spring Data JPA Repository interface for ContactMessage entity persistence operations.
 * Provides custom derived query methods for duplicate message detection and submission metric aggregation.
 */
@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

    /**
     * Finds contact submissions from a specific email created after the given timestamp.
     * Used for enforcing duplicate submission window security rules (e.g. 5-minute sliding window).
     *
     * @param email Target email address to query
     * @param after Cutoff timestamp limit
     * @return List of matching ContactMessage entities
     */
    List<ContactMessage> findByEmailAndCreatedAtAfter(String email, LocalDateTime after);

    /**
     * Counts the total number of contact submissions created between start and end timestamps.
     * Used for system analytics and reporting dashboards.
     *
     * @param start Lower bound timestamp constraint
     * @param end Upper bound timestamp constraint
     * @return Total count of matching submissions
     */
    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
