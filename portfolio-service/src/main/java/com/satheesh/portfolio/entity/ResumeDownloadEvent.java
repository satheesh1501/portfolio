package com.satheesh.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description JPA Entity representing resume PDF download audit events.
 * Maps to PostgreSQL table 'resume_download_events'. Stores IP address, User-Agent, referer header,
 * and download timestamp for security analytics and reporting.
 */
@Entity
@Table(name = "resume_download_events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDownloadEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** IPv4 or IPv6 address of the downloader. */
    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    /** Full User-Agent header for browser and device metric analytics. */
    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    /** HTTP Referer header identifying traffic origin. */
    @Column(length = 500)
    private String referer;

    @CreationTimestamp
    @Column(name = "downloaded_at", nullable = false, updatable = false)
    private LocalDateTime downloadedAt;
}
