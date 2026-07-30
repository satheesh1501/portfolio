package com.satheesh.portfolio.service;

import com.satheesh.portfolio.dto.ContactRequestDTO;
import com.satheesh.portfolio.dto.ContactResponseDTO;
import com.satheesh.portfolio.entity.ContactMessage;
import com.satheesh.portfolio.enums.ContactStatus;
import com.satheesh.portfolio.exception.DuplicateSubmissionException;
import com.satheesh.portfolio.kafka.ContactEventProducer;
import com.satheesh.portfolio.mapper.ContactMapper;
import com.satheesh.portfolio.repository.ContactMessageRepository;
import com.satheesh.portfolio.security.RateLimiterService;
import com.satheesh.portfolio.service.impl.ContactServiceImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ContactServiceImpl contactService;

    private ContactRequestDTO requestDTO;
    private ContactMessage contactEntity;
    private ContactResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new ContactRequestDTO("John Doe", "john@example.com", "Project Inquiry", "I want to build an enterprise web application.");
        
        contactEntity = new ContactMessage();
        contactEntity.setId(1L);
        contactEntity.setName("John Doe");
        contactEntity.setEmail("john@example.com");
        contactEntity.setSubject("Project Inquiry");
        contactEntity.setMessage("I want to build an enterprise web application.");
        contactEntity.setIpAddress("192.168.1.100");
        contactEntity.setStatus(ContactStatus.PENDING);
        contactEntity.setCreatedAt(LocalDateTime.now());

        responseDTO = new ContactResponseDTO(1L, "Your message has been received. We will get back to you soon!", LocalDateTime.now());
    }

    @Test
    @DisplayName("Process Contact Submission - Success Flow")
    void processContactSubmission_Success() {
        when(repository.findByEmailAndCreatedAtAfter(eq("john@example.com"), any())).thenReturn(Collections.emptyList());
        when(mapper.toEntity(any(ContactRequestDTO.class))).thenReturn(contactEntity);
        when(repository.save(any(ContactMessage.class))).thenReturn(contactEntity);
        when(mapper.toResponseDTO(any(ContactMessage.class))).thenReturn(responseDTO);

        ContactResponseDTO result = contactService.processContactSubmission(requestDTO, "192.168.1.100");

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(rateLimiterService, times(1)).checkRateLimit(any(), eq("192.168.1.100"), anyInt(), anyInt());
        verify(repository, times(2)).save(any(ContactMessage.class));
    }

    @Test
    @DisplayName("Process Contact Submission - Duplicate Window Rejection")
    void processContactSubmission_DuplicateRejection() {
        when(repository.findByEmailAndCreatedAtAfter(eq("john@example.com"), any()))
                .thenReturn(List.of(contactEntity));

        assertThrows(DuplicateSubmissionException.class, () -> {
            contactService.processContactSubmission(requestDTO, "192.168.1.100");
        });

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Process Contact Submission - Localhost E2E Bypass")
    void processContactSubmission_LocalhostBypass() {
        when(mapper.toEntity(any(ContactRequestDTO.class))).thenReturn(contactEntity);
        when(repository.save(any(ContactMessage.class))).thenReturn(contactEntity);
        when(mapper.toResponseDTO(any(ContactMessage.class))).thenReturn(responseDTO);

        ContactResponseDTO result = contactService.processContactSubmission(requestDTO, "127.0.0.1");

        assertNotNull(result);
        verify(rateLimiterService, never()).checkRateLimit(any(), any(), anyInt(), anyInt());
    }
}
