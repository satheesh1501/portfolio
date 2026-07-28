package com.satheesh.portfolio.enums;

/**
 * Lifecycle status for contact form submissions.
 * PENDING  → Submitted; Kafka event not yet published.
 * NOTIFIED → Email notification delivered via notification-service.
 * FAILED   → Kafka publish or email delivery failed.
 */
public enum ContactStatus {
    PENDING,
    NOTIFIED,
    FAILED
}
