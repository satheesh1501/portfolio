package com.satheesh.portfolio.dto;

import com.satheesh.common.validation.NoHtml;
import jakarta.validation.constraints.*;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Immutable record DTO representing incoming AI chatbot prompts.
 * Includes Bean Validation for character length (1-500) and custom @NoHtml security annotation
 * to prevent prompt injection and XSS payload attacks.
 *
 * @param message The input chat query string from the user
 * @param sessionId Optional session identifier for maintaining multi-turn conversational context
 */
public record ChatMessageDTO(

        @NotBlank(message = "Message is required")
        @Size(min = 1, max = 500, message = "Message must not exceed 500 characters")
        @NoHtml
        String message,

        String sessionId

) {}
