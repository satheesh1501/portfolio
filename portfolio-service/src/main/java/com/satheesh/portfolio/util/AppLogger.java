package com.satheesh.portfolio.util;

import com.satheesh.portfolio.constants.AppConstants;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Centralized structured logging utility.
 * Enforces uniform log format across the microservice:
 * "[Portfolio-Service] [TraceID: {}] [IP: {}] [Class: {}] [Method: {}] - {}: {}"
 */
public final class AppLogger {

    private static final String LOG_TEMPLATE = "[Portfolio-Service] [TraceID: {}] [IP: {}] [Class: {}] [Method: {}] - {}: {}";

    private AppLogger() {
        // Private constructor to prevent instantiation
    }

    /**
     * Retrieves current TraceID from MDC context or generates a new one.
     * 
     * @return String TraceID
     */
    public static String getTraceId() {
        String traceId = MDC.get(AppConstants.HEADER_TRACE_ID);
        if (traceId == null || traceId.isBlank()) {
            traceId = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
            MDC.put(AppConstants.HEADER_TRACE_ID, traceId);
        }
        return traceId;
    }

    /**
     * Sanitizes IP address to prevent log injection.
     */
    private static String sanitizeIp(String ip) {
        if (ip == null || ip.isBlank()) {
            return AppConstants.UNKNOWN_IP;
        }
        return ip.replaceAll("[\r\n]", "");
    }

    /**
     * Logs an INFO level message using the standardized template.
     */
    public static void info(Logger log, String className, String methodName, String ip, String action, String message) {
        log.info(LOG_TEMPLATE, getTraceId(), sanitizeIp(ip), className, methodName, action, message);
    }

    /**
     * Logs a WARN level message using the standardized template.
     */
    public static void warn(Logger log, String className, String methodName, String ip, String action, String message) {
        log.warn(LOG_TEMPLATE, getTraceId(), sanitizeIp(ip), className, methodName, action, message);
    }

    /**
     * Logs an ERROR level message using the standardized template with exception.
     */
    public static void error(Logger log, String className, String methodName, String ip, String action, String message, Throwable t) {
        log.error(LOG_TEMPLATE, getTraceId(), sanitizeIp(ip), className, methodName, action, message, t);
    }
}
