package com.satheesh.portfolio.constants;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Centralized repository for user-facing responses, error messages, and log messages.
 * Prevents text duplication and ensures uniform messaging across APIs and exceptions.
 */
public final class MessageConstants {

    private MessageConstants() {
        // Private constructor to prevent instantiation
    }

    // Success Messages
    public static final String CONTACT_SUCCESS_MESSAGE = "Your message has been received. We will get back to you soon!";
    public static final String RESUME_DOWNLOAD_SUCCESS = "Resume download tracked successfully.";

    // Exception Error Messages
    public static final String RATE_LIMIT_EXCEEDED = "Too many requests. Please try again after some time.";
    public static final String DUPLICATE_SUBMISSION = "You have already submitted a message recently. Please wait a few minutes before trying again.";
    public static final String RESOURCE_NOT_FOUND = "Requested resource was not found.";
    public static final String INVALID_INPUT = "Validation failed for input fields.";
    public static final String INTERNAL_SERVER_ERROR = "An unexpected error occurred. Please try again later.";
    public static final String AI_SERVICE_UNAVAILABLE = "AI Chatbot is currently unavailable. Please try again shortly.";

    // Log Action Labels
    public static final String LOG_ACTION_SUBMIT_CONTACT = "SUBMIT_CONTACT";
    public static final String LOG_ACTION_GET_PROJECTS = "GET_PROJECTS";
    public static final String LOG_ACTION_GET_PROJECT_BY_ID = "GET_PROJECT_BY_ID";
    public static final String LOG_ACTION_TRACK_RESUME = "TRACK_RESUME_DOWNLOAD";
    public static final String LOG_ACTION_PROCESS_CHAT = "PROCESS_CHAT_MESSAGE";
    public static final String LOG_ACTION_KAFKA_PUBLISH = "KAFKA_EVENT_PUBLISH";
    public static final String LOG_ACTION_RATE_LIMIT = "RATE_LIMIT_CHECK";
}
