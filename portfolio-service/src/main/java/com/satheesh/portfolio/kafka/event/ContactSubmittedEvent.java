package com.satheesh.portfolio.kafka.event;

import java.time.LocalDateTime;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Immutable event payload published to Apache Kafka when a contact form is submitted.
 * Consumed by notification-service to trigger asynchronous email notifications.
 */
public record ContactSubmittedEvent(
        Long messageId,
        String name,
        String email,
        String subject,
        String message,
        String ipAddress,
        LocalDateTime submittedAt
) {}
