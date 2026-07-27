package com.satheesh.notification.service.impl;

import com.satheesh.notification.kafka.event.ContactSubmittedEvent;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Unit tests for EmailServiceImpl Thymeleaf HTML email rendering and JavaMailSender delivery.
 */
@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private EmailServiceImpl emailService;

    private ContactSubmittedEvent event;

    @BeforeEach
    void setUp() {
        event = new ContactSubmittedEvent(
                1L,
                "Alex Johnson",
                "alex@example.com",
                "Job Opportunity",
                "Hello Satheesh, great microservices platform!",
                "192.168.1.10",
                LocalDateTime.now()
        );
    }

    @Test
    @DisplayName("Should successfully process Thymeleaf template and send email via JavaMailSender")
    void testSendContactNotificationEmailSuccess() {
        when(templateEngine.process(anyString(), any(Context.class))).thenReturn("<html>Test Email</html>");
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        assertDoesNotThrow(() -> emailService.sendContactNotificationEmail(event));

        verify(templateEngine, times(1)).process(eq("email/contact-notification"), any(Context.class));
        verify(mailSender, times(1)).send(any(MimeMessage.class));
    }
}
