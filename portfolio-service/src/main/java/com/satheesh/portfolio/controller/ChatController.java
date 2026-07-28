package com.satheesh.portfolio.controller;

import com.satheesh.common.constants.AppConstants;
import com.satheesh.portfolio.dto.ChatMessageDTO;
import com.satheesh.portfolio.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description REST Controller for interacting with the Spring AI Gemini Chatbot.
 */
@RestController
@RequestMapping(AppConstants.CHAT_PATH)
@RequiredArgsConstructor
@Tag(name = "AI Chat API", description = "Endpoints for interacting with the AI Portfolio Assistant")
public class ChatController {

    private final ChatService chatService;

    /**
     * Processes user chat prompt and returns AI response.
     * 
     * @param chatDTO Input message DTO
     * @param request HttpServletRequest for IP extraction
     * @return ResponseEntity with AI reply
     */
    @PostMapping
    @Operation(summary = "Process AI Chat Message", description = "Rate-limits and returns contextual AI assistant response")
    public ResponseEntity<Map<String, String>> processChat(
            @Valid @RequestBody ChatMessageDTO chatDTO,
            HttpServletRequest request) {

        String ipAddress = extractClientIp(request);
        String reply = chatService.processChat(chatDTO, ipAddress);
        return ResponseEntity.ok(Map.of("reply", reply));
    }

    private String extractClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader(AppConstants.HEADER_FORWARDED_FOR);
        if (xfHeader == null || xfHeader.isBlank()) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0].trim();
    }
}
