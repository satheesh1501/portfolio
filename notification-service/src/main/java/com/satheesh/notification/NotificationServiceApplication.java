package com.satheesh.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Main application entry point for notification-service microservice.
 * Listens to Apache Kafka events and sends HTML email notifications.
 */
@SpringBootApplication(scanBasePackages = {"com.satheesh.notification", "com.satheesh.common"})
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}
