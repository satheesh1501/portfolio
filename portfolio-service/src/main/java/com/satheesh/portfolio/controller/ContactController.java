package com.satheesh.portfolio.controller;

import com.satheesh.common.constants.AppConstants;
import com.satheesh.portfolio.dto.ContactRequestDTO;
import com.satheesh.portfolio.dto.ContactResponseDTO;
import com.satheesh.portfolio.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description REST Controller for processing portfolio contact form submissions.
 */
@RestController
@RequestMapping(AppConstants.CONTACT_PATH)
@RequiredArgsConstructor
@Tag(name = "Contact API", description = "Endpoints for submitting contact form messages")
public class ContactController {

    private final ContactService contactService;

    /**
     * Handles POST submission of contact form messages.
     * 
     * @param requestDTO Validated request body
     * @param request HttpServletRequest for IP extraction
     * @return ResponseEntity containing ContactResponseDTO
     */
    @PostMapping
    @Operation(summary = "Submit Contact Form", description = "Validates, rate-limits, persists contact submission, and triggers email notification via Kafka")
    public ResponseEntity<ContactResponseDTO> submitContact(
            @Valid @RequestBody ContactRequestDTO requestDTO,
            HttpServletRequest request) {

        String ipAddress = extractClientIp(request);
        ContactResponseDTO response = contactService.processContactSubmission(requestDTO, ipAddress);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private String extractClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader(AppConstants.HEADER_FORWARDED_FOR);
        if (xfHeader == null || xfHeader.isBlank()) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0].trim();
    }
}
