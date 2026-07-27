package com.satheesh.portfolio.repository;

import com.satheesh.portfolio.entity.ResumeDownloadEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * Spring Data JPA repository for ResumeDownloadEvent entities.
 *
 * Insert-only by design — resume download events are never updated or deleted.
 * The countByDownloadedAtBetween method powers the resume download analytics
 * dashboard (daily, weekly, monthly download counts).
 */
@Repository
public interface ResumeDownloadRepository extends JpaRepository<ResumeDownloadEvent, Long> {

    /**
     * Counts resume downloads within a given date/time range.
     * Usage: countByDownloadedAtBetween(
     *            LocalDateTime.now().minusDays(7), LocalDateTime.now()
     *        )
     */
    long countByDownloadedAtBetween(LocalDateTime start, LocalDateTime end);
}
