package com.satheesh.portfolio.dto;

import java.time.LocalDateTime;

/**
 * Immutable response DTO returned to the frontend after a successful
 * contact form submission.
 *
 * Only exposes safe, user-facing fields — no internal DB fields leaked.
 */
public record ContactResponseDTO(
        Long id,
        String message,
        LocalDateTime timestamp
) {}
