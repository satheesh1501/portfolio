package com.satheesh.portfolio.service;

import com.satheesh.portfolio.dto.ChatMessageDTO;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Service interface for processing Spring AI Gemini Chatbot messages.
 */
public interface ChatService {

    /**
     * Processes a user chat prompt and returns an AI response.
     * 
     * @param chatDTO Input message DTO
     * @param ipAddress Client IP address
     * @return AI response string
     */
    String processChat(ChatMessageDTO chatDTO, String ipAddress);
}
