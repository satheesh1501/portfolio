package com.satheesh.portfolio.dto;

import java.time.LocalDateTime;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Immutable response DTO returned to the client UI following contact form persistence.
 * Exposes safe, public confirmation attributes without leaking internal database metadata or IP audit details.
 *
 * @param id Unique generated primary key ID of the persisted contact record
 * @param message User-facing confirmation message
 * @param timestamp Server timestamp when the contact message was received
 */
public record ContactResponseDTO(
        Long id,
        String message,
        LocalDateTime timestamp
) {}
