package com.satheesh.portfolio.service;

import com.satheesh.portfolio.dto.ContactRequestDTO;
import com.satheesh.portfolio.dto.ContactResponseDTO;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Service interface for contact form processing and workflow orchestration.
 */
public interface ContactService {

    /**
     * Processes an incoming contact form submission.
     * Checks rate limits, duplicate email windows, persists entity, and publishes Kafka event.
     * 
     * @param requestDTO User input payload
     * @param ipAddress Client IP address
     * @return ContactResponseDTO Confirmation containing message ID and timestamp
     */
    ContactResponseDTO processContactSubmission(ContactRequestDTO requestDTO, String ipAddress);
}
