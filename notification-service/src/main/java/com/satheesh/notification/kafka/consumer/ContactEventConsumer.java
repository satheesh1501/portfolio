package com.satheesh.notification.kafka.consumer;

import com.satheesh.common.constants.AppConstants;
import com.satheesh.common.constants.MessageConstants;
import com.satheesh.common.util.AppLogger;
import com.satheesh.notification.kafka.event.ContactSubmittedEvent;
import com.satheesh.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Kafka Event Consumer listening to contact-notifications-topic.
 */
@Component
@RequiredArgsConstructor
public class ContactEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(ContactEventConsumer.class);
    private static final String SERVICE_NAME = "Notification-Service";
    private static final String CLASS_NAME = ContactEventConsumer.class.getSimpleName();

    private final EmailService emailService;

    @KafkaListener(
            topics = AppConstants.KAFKA_CONTACT_TOPIC,
            groupId = AppConstants.KAFKA_NOTIFICATION_GROUP_ID,
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeContactEvent(ContactSubmittedEvent event) {
        String methodName = "consumeContactEvent";

        AppLogger.info(log, SERVICE_NAME, CLASS_NAME, methodName, event.ipAddress(), MessageConstants.LOG_ACTION_KAFKA_CONSUME,
                "Received Kafka event for messageId: " + event.messageId() + " from: " + event.email());

        emailService.sendContactNotificationEmail(event);
    }
}
