package com.satheesh.portfolio.config;

import com.satheesh.portfolio.constants.AppConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Configuration for Apache Kafka producers and topic provisioner.
 * Automatically creates contact-notifications-topic on broker startup if missing.
 */
@Configuration
public class KafkaProducerConfig {

    @Bean
    public NewTopic contactNotificationsTopic() {
        return TopicBuilder.name(AppConstants.KAFKA_CONTACT_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
