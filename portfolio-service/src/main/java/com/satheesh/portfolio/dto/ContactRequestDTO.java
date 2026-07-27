package com.satheesh.portfolio.dto;

import com.satheesh.portfolio.validation.NoHtml;
import jakarta.validation.constraints.*;

/**
 * Immutable request DTO for incoming contact form submissions.
 *
 * Applies 4-layer security validation on every user-submitted field:
 *   Layer 1 — @NotBlank : field must not be null or empty string
 *   Layer 2 — @Size     : enforces min/max character length
 *   Layer 3 — @Pattern  : whitelist — only safe characters allowed
 *   Layer 4 — @NoHtml   : custom — blocks HTML tags and JS injection (XSS)
 *
 * Uses Java 21 Record for zero-boilerplate immutable data carrier.
 */
public record ContactRequestDTO(

        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        @Pattern(
                regexp = "^[\\p{L} .,'\\-]+$",
                message = "Name can only contain letters, spaces, dots, commas, apostrophes and hyphens"
        )
        @NoHtml
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Please provide a valid email address")
        @Size(max = 150, message = "Email must not exceed 150 characters")
        String email,

        @NotBlank(message = "Subject is required")
        @Size(min = 5, max = 200, message = "Subject must be between 5 and 200 characters")
        @Pattern(
                regexp = "^[\\p{L}\\p{N} ,.!?'()\\-_:;@&+]*$",
                message = "Subject contains invalid characters"
        )
        @NoHtml
        String subject,

        @NotBlank(message = "Message is required")
        @Size(min = 10, max = 2000, message = "Message must be between 10 and 2000 characters")
        @NoHtml
        String message

) {}
