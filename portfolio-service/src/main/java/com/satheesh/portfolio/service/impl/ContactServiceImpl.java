package com.satheesh.portfolio.service.impl;

import com.satheesh.common.constants.AppConstants;
import com.satheesh.common.constants.MessageConstants;
import com.satheesh.portfolio.dto.ContactRequestDTO;
import com.satheesh.portfolio.dto.ContactResponseDTO;
import com.satheesh.portfolio.entity.ContactMessage;
import com.satheesh.portfolio.enums.ContactStatus;
import com.satheesh.portfolio.exception.DuplicateSubmissionException;
import com.satheesh.portfolio.kafka.ContactEventProducer;
import com.satheesh.portfolio.kafka.event.ContactSubmittedEvent;
import com.satheesh.portfolio.mapper.ContactMapper;
import com.satheesh.portfolio.repository.ContactMessageRepository;
import com.satheesh.portfolio.security.RateLimiterService;
import com.satheesh.portfolio.service.ContactService;
import com.satheesh.common.util.AppLogger;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.2.0
 * 
 * @description Service implementation for contact form workflow.
 * Manages Redis rate limiting, duplicate detection, PostgreSQL persistence, and Kafka publishing.
 * Includes loopback IP rate-limit bypass for E2E testing.
 */
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private static final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);
    private static final String CLASS_NAME = ContactServiceImpl.class.getSimpleName();

    private final ContactMessageRepository repository;
    private final ContactMapper mapper;
    private final RateLimiterService rateLimiterService;
    private final ContactEventProducer eventProducer;

    @Override
    @Transactional
    public ContactResponseDTO processContactSubmission(ContactRequestDTO requestDTO, String ipAddress) {
        String methodName = "processContactSubmission";

        // Step 1: Redis Rate Limiter Check (3 requests / 15 mins per IP, bypass for localhost E2E testing)
        if (!isLocalhost(ipAddress)) {
            rateLimiterService.checkRateLimit(
                    AppConstants.REDIS_RATE_LIMIT_CONTACT_PREFIX,
                    ipAddress,
                    AppConstants.CONTACT_RATE_LIMIT,
                    AppConstants.CONTACT_RATE_LIMIT_WINDOW_MINUTES
            );
        }

        // Step 2: Duplicate Email Window Check (within last 5 minutes, bypass for random test emails)
        if (!isLocalhost(ipAddress)) {
            LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);
            List<ContactMessage> duplicates = repository.findByEmailAndCreatedAtAfter(requestDTO.email(), fiveMinutesAgo);
            if (!duplicates.isEmpty()) {
                AppLogger.warn(log, "Portfolio-Service", CLASS_NAME, methodName, ipAddress, MessageConstants.LOG_ACTION_SUBMIT_CONTACT,
                        "Duplicate submission detected for email: " + requestDTO.email());
                throw new DuplicateSubmissionException(MessageConstants.DUPLICATE_SUBMISSION);
            }
        }

        // Step 3: Convert DTO to Entity & Persist to PostgreSQL
        ContactMessage entity = mapper.toEntity(requestDTO);
        entity.setIpAddress(ipAddress);
        entity.setStatus(ContactStatus.PENDING);
        ContactMessage savedEntity = repository.save(entity);

        AppLogger.info(log, "Portfolio-Service", CLASS_NAME, methodName, ipAddress, MessageConstants.LOG_ACTION_SUBMIT_CONTACT,
                "Saved contact message to PostgreSQL with ID: " + savedEntity.getId());

        // Step 4: Publish Event to Apache Kafka
        ContactSubmittedEvent event = new ContactSubmittedEvent(
                savedEntity.getId(),
                savedEntity.getName(),
                savedEntity.getEmail(),
                savedEntity.getSubject(),
                savedEntity.getMessage(),
                ipAddress,
                savedEntity.getCreatedAt()
        );
        eventProducer.sendContactEvent(event);

        // Update status to NOTIFIED after publishing event
        savedEntity.setStatus(ContactStatus.NOTIFIED);
        repository.save(savedEntity);

        // Step 5: Return Response DTO
        return mapper.toResponseDTO(savedEntity);
    }

    private boolean isLocalhost(String ip) {
        return ip == null || "127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip) || ip.startsWith("127.") || ip.startsWith("localhost");
    }
}
