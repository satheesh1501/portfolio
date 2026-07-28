package com.satheesh.notification.kafka.event;

import java.time.LocalDateTime;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Consumer event payload record matching ContactSubmittedEvent published by portfolio-service.
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
