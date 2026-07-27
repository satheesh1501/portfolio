package com.satheesh.portfolio.repository;

import com.satheesh.portfolio.entity.ResumeDownloadEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Spring Data JPA Repository interface for ResumeDownloadEvent tracking.
 * Insert-only repository powering backend resume download analytics and audit reporting.
 */
@Repository
public interface ResumeDownloadRepository extends JpaRepository<ResumeDownloadEvent, Long> {

    /**
     * Counts the total number of resume download events within a specified date/time interval.
     * Powers admin analytics metric charts (e.g., weekly/monthly PDF download totals).
     *
     * @param start Lower bound timestamp
     * @param end Upper bound timestamp
     * @return Total number of recorded resume download events
     */
    long countByDownloadedAtBetween(LocalDateTime start, LocalDateTime end);
}
