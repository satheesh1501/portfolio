package com.satheesh.portfolio.dto;

import com.satheesh.portfolio.validation.NoHtml;
import jakarta.validation.constraints.*;

/**
 * Immutable request DTO for incoming AI chatbot messages.
 *
 * sessionId is optional — when provided it allows the Spring AI
 * conversation memory to maintain chat history for a browser session.
 *
 * @NoHtml guards against prompt injection via HTML/JS payloads.
 */
public record ChatMessageDTO(

        @NotBlank(message = "Message is required")
        @Size(min = 1, max = 500, message = "Message must not exceed 500 characters")
        @NoHtml
        String message,

        String sessionId

) {}
