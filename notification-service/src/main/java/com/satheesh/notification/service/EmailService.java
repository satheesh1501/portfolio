package com.satheesh.notification.service;

import com.satheesh.notification.kafka.event.ContactSubmittedEvent;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Service interface for rendering and sending email notifications.
 */
public interface EmailService {

    /**
     * Sends an HTML contact notification email to the portfolio owner.
     * 
     * @param event ContactSubmittedEvent payload
     */
    void sendContactNotificationEmail(ContactSubmittedEvent event);
}
