package com.satheesh.common.constants;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Shared repository for application-wide system constants across all microservices.
 */
public final class AppConstants {

    private AppConstants() {
        // Private constructor to prevent instantiation
    }

    // Base API Paths
    public static final String API_V1_BASE = "/api/v1";
    public static final String CONTACT_PATH = API_V1_BASE + "/contact";
    public static final String PROJECTS_PATH = API_V1_BASE + "/projects";
    public static final String RESUME_PATH = API_V1_BASE + "/resume";
    public static final String CHAT_PATH = API_V1_BASE + "/chat";

    // Kafka Topics & Group IDs
    public static final String KAFKA_CONTACT_TOPIC = "contact-notifications-topic";
    public static final String KAFKA_PORTFOLIO_GROUP_ID = "portfolio-service-group";
    public static final String KAFKA_NOTIFICATION_GROUP_ID = "notification-service-group";

    // Redis Rate Limiting Keys & Prefixes
    public static final String REDIS_RATE_LIMIT_CONTACT_PREFIX = "rate_limit:contact:";
    public static final String REDIS_RATE_LIMIT_CHAT_PREFIX = "rate_limit:chat:";

    // Rate Limiting Thresholds
    public static final int CONTACT_RATE_LIMIT = 3;
    public static final int CONTACT_RATE_LIMIT_WINDOW_MINUTES = 15;
    public static final int CHAT_RATE_LIMIT = 10;
    public static final int CHAT_RATE_LIMIT_WINDOW_MINUTES = 5;

    // HTTP Headers & Tracing
    public static final String HEADER_TRACE_ID = "X-Trace-Id";
    public static final String HEADER_FORWARDED_FOR = "X-Forwarded-For";
    public static final String UNKNOWN_IP = "UNKNOWN_IP";

    // Email Settings
    public static final String NOTIFICATION_EMAIL_TO = "psatheeshkumar1501@gmail.com";

    // Cache Names
    public static final String CACHE_PROJECTS = "projects";
    public static final String CACHE_FEATURED_PROJECTS = "featured_projects";
}
