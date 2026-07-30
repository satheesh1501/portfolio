package com.satheesh.portfolio.service;

import com.satheesh.portfolio.kafka.event.ContactSubmittedEvent;

public interface EmailService {
    void sendContactNotificationEmail(ContactSubmittedEvent event);
}
