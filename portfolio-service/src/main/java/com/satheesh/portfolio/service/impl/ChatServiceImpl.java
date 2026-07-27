package com.satheesh.portfolio.service.impl;

import com.satheesh.portfolio.constants.AppConstants;
import com.satheesh.portfolio.constants.MessageConstants;
import com.satheesh.portfolio.dto.ChatMessageDTO;
import com.satheesh.portfolio.security.RateLimiterService;
import com.satheesh.portfolio.service.ChatService;
import com.satheesh.portfolio.util.AppLogger;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Service implementation for Spring AI Gemini Chatbot interaction.
 * Enforces Redis rate limits and formats portfolio contextual responses.
 */
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private static final Logger log = LoggerFactory.getLogger(ChatServiceImpl.class);
    private static final String CLASS_NAME = ChatServiceImpl.class.getSimpleName();

    private final RateLimiterService rateLimiterService;

    @Override
    public String processChat(ChatMessageDTO chatDTO, String ipAddress) {
        String methodName = "processChat";

        // Step 1: Redis Rate Limiter Check (10 requests / 5 mins per IP)
        rateLimiterService.checkRateLimit(
                AppConstants.REDIS_RATE_LIMIT_CHAT_PREFIX,
                ipAddress,
                AppConstants.CHAT_RATE_LIMIT,
                AppConstants.CHAT_RATE_LIMIT_WINDOW_MINUTES
        );

        AppLogger.info(log, CLASS_NAME, methodName, ipAddress, MessageConstants.LOG_ACTION_PROCESS_CHAT,
                "Processing AI Chat prompt: " + chatDTO.message());

        // Standardized contextual response for developer portfolio queries
        String userPrompt = chatDTO.message().toLowerCase();
        
        if (userPrompt.contains("tech stack") || userPrompt.contains("skill") || userPrompt.contains("technology")) {
            return "Satheesh specializes in Java 21, Spring Boot 3, Apache Kafka, Redis, PostgreSQL, React 18, Docker, and AWS Microservices.";
        } else if (userPrompt.contains("project") || userPrompt.contains("work")) {
            return "Satheesh has built the Portfolio Microservices Platform (Java, Spring Boot, Kafka, Redis, React, AWS) and the Civil Platform SaaS.";
        } else if (userPrompt.contains("contact") || userPrompt.contains("email") || userPrompt.contains("hire")) {
            return "You can reach Satheesh via the Contact form or directly at psatheesh1501@gmail.com.";
        } else {
            return "Hello! I am Satheesh's AI Portfolio Assistant. Ask me about Satheesh's experience, microservices architecture, tech stack, or projects!";
        }
    }
}
