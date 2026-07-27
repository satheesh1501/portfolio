package com.satheesh.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * JPA entity to track resume download events.
 * Maps to the 'resume_download_events' table created by V2 Flyway migration.
 *
 * Lightweight event record — no updates, only inserts.
 * Used for analytics: download count, peak times, geographic distribution.
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

    /** IPv4 or IPv6 address of the downloader (max 45 chars for IPv6). */
    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    /** Full User-Agent header for device/browser analytics. */
    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    /** HTTP Referer header — identifies where the visitor came from. */
    @Column(length = 500)
    private String referer;

    @CreationTimestamp
    @Column(name = "downloaded_at", nullable = false, updatable = false)
    private LocalDateTime downloadedAt;
}
