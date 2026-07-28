package com.satheesh.notification.service.impl;

import com.satheesh.common.constants.AppConstants;
import com.satheesh.common.constants.MessageConstants;
import com.satheesh.common.util.AppLogger;
import com.satheesh.notification.kafka.event.ContactSubmittedEvent;
import com.satheesh.notification.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Service implementation for sending Thymeleaf HTML email notifications.
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    private static final String SERVICE_NAME = "Notification-Service";
    private static final String CLASS_NAME = EmailServiceImpl.class.getSimpleName();

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void sendContactNotificationEmail(ContactSubmittedEvent event) {
        String methodName = "sendContactNotificationEmail";
        
        AppLogger.info(log, SERVICE_NAME, CLASS_NAME, methodName, event.ipAddress(), MessageConstants.LOG_ACTION_SEND_EMAIL,
                "Rendering email template for messageId: " + event.messageId() + " from sender: " + event.email());

        try {
            Context context = new Context();
            context.setVariable("name", event.name());
            context.setVariable("email", event.email());
            context.setVariable("subject", event.subject());
            context.setVariable("message", event.message());
            context.setVariable("ipAddress", event.ipAddress());
            context.setVariable("submittedAt", event.submittedAt());

            String htmlContent = templateEngine.process("email/contact-notification", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            helper.setTo(AppConstants.NOTIFICATION_EMAIL_TO);
            helper.setSubject("Portfolio Notification: " + event.subject());
            helper.setText(htmlContent, true);
            helper.setFrom("no-reply@satheesh-portfolio.com", "Portfolio Notification System");

            mailSender.send(message);

            AppLogger.info(log, SERVICE_NAME, CLASS_NAME, methodName, event.ipAddress(), MessageConstants.LOG_ACTION_SEND_EMAIL,
                    "Email notification successfully delivered to Mailpit/SMTP for messageId: " + event.messageId());

        } catch (Exception e) {
            AppLogger.error(log, SERVICE_NAME, CLASS_NAME, methodName, event.ipAddress(), MessageConstants.LOG_ACTION_SEND_EMAIL,
                    "Failed to send email notification for messageId: " + event.messageId(), e);
        }
    }
}
