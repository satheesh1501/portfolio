package com.satheesh.portfolio.service.impl;

import com.satheesh.portfolio.dto.ContactRequestDTO;
import com.satheesh.portfolio.dto.ContactResponseDTO;
import com.satheesh.portfolio.entity.ContactMessage;
import com.satheesh.portfolio.enums.ContactStatus;
import com.satheesh.portfolio.exception.DuplicateSubmissionException;
import com.satheesh.portfolio.kafka.ContactEventProducer;
import com.satheesh.portfolio.mapper.ContactMapper;
import com.satheesh.portfolio.repository.ContactMessageRepository;
import com.satheesh.portfolio.security.RateLimiterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Unit tests for ContactServiceImpl workflow and duplicate email prevention.
 */
@ExtendWith(MockitoExtension.class)
class ContactServiceImplTest {

    @Mock
    private ContactMessageRepository repository;

    @Mock
    private ContactMapper mapper;

    @Mock
    private RateLimiterService rateLimiterService;

    @Mock
    private ContactEventProducer eventProducer;

    @InjectMocks
    private ContactServiceImpl contactService;

    private ContactRequestDTO requestDTO;
    private ContactMessage entity;
    private ContactResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new ContactRequestDTO("John Doe", "john@example.com", "Job Opportunity", "Hello Satheesh!");
        entity = ContactMessage.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .subject("Job Opportunity")
                .message("Hello Satheesh!")
                .ipAddress("127.0.0.1")
                .status(ContactStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        responseDTO = new ContactResponseDTO(1L, "Success", LocalDateTime.now());
    }

    @Test
    @DisplayName("Should successfully process contact submission when input is valid and non-duplicate")
    void testProcessContactSubmissionSuccess() {
        when(repository.findByEmailAndCreatedAtAfter(anyString(), any())).thenReturn(Collections.emptyList());
        when(mapper.toEntity(any())).thenReturn(entity);
        when(repository.save(any())).thenReturn(entity);
        when(mapper.toResponseDTO(any())).thenReturn(responseDTO);

        ContactResponseDTO result = contactService.processContactSubmission(requestDTO, "127.0.0.1");

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(rateLimiterService, times(1)).checkRateLimit(anyString(), anyString(), anyInt(), anyInt());
        verify(eventProducer, times(1)).sendContactEvent(any());
        verify(repository, times(2)).save(any());
    }

    @Test
    @DisplayName("Should throw DuplicateSubmissionException when email was submitted within 5 minutes")
    void testProcessContactSubmissionDuplicate() {
        when(repository.findByEmailAndCreatedAtAfter(anyString(), any())).thenReturn(List.of(entity));

        assertThrows(DuplicateSubmissionException.class, () ->
                contactService.processContactSubmission(requestDTO, "127.0.0.1")
        );

        verify(eventProducer, never()).sendContactEvent(any());
    }
}
