package com.satheesh.portfolio.kafka;

import com.satheesh.portfolio.constants.AppConstants;
import com.satheesh.portfolio.constants.MessageConstants;
import com.satheesh.portfolio.kafka.event.ContactSubmittedEvent;
import com.satheesh.portfolio.util.AppLogger;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Event producer for publishing contact form submission events to Apache Kafka.
 */
@Component
@RequiredArgsConstructor
public class ContactEventProducer {

    private static final Logger log = LoggerFactory.getLogger(ContactEventProducer.class);
    private static final String CLASS_NAME = ContactEventProducer.class.getSimpleName();

    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Publishes a ContactSubmittedEvent to Kafka topic asynchronously.
     * 
     * @param event ContactSubmittedEvent record
     */
    public void sendContactEvent(ContactSubmittedEvent event) {
        String key = String.valueOf(event.messageId());
        
        AppLogger.info(log, CLASS_NAME, "sendContactEvent", event.ipAddress(), MessageConstants.LOG_ACTION_KAFKA_PUBLISH,
                "Publishing contact event for messageId: " + event.messageId() + " to topic: " + AppConstants.KAFKA_CONTACT_TOPIC);

        kafkaTemplate.send(AppConstants.KAFKA_CONTACT_TOPIC, key, event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        AppLogger.info(log, CLASS_NAME, "sendContactEvent", event.ipAddress(), MessageConstants.LOG_ACTION_KAFKA_PUBLISH,
                                "Successfully published event to Kafka offset: " + result.getRecordMetadata().offset());
                    } else {
                        AppLogger.error(log, CLASS_NAME, "sendContactEvent", event.ipAddress(), MessageConstants.LOG_ACTION_KAFKA_PUBLISH,
                                "Failed to publish contact event to Kafka for messageId: " + event.messageId(), ex);
                    }
                });
    }
}
