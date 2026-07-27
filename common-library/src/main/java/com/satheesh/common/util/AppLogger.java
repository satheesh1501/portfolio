package com.satheesh.common.util;

import com.satheesh.common.constants.AppConstants;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Shared enterprise structured logging utility.
 * Enforces uniform log format across all microservices:
 * "[Service-Name] [TraceID: {}] [IP: {}] [Class: {}] [Method: {}] - {}: {}"
 */
public final class AppLogger {

    private static final String LOG_TEMPLATE = "[{}] [TraceID: {}] [IP: {}] [Class: {}] [Method: {}] - {}: {}";

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
     * Sets explicit TraceID into MDC context.
     * 
     * @param traceId Custom trace ID
     */
    public static void setTraceId(String traceId) {
        if (traceId != null && !traceId.isBlank()) {
            MDC.put(AppConstants.HEADER_TRACE_ID, traceId);
        }
    }

    private static String sanitizeIp(String ip) {
        if (ip == null || ip.isBlank()) {
            return AppConstants.UNKNOWN_IP;
        }
        return ip.replaceAll("[\r\n]", "");
    }

    public static void info(Logger log, String serviceName, String className, String methodName, String ip, String action, String message) {
        log.info(LOG_TEMPLATE, serviceName, getTraceId(), sanitizeIp(ip), className, methodName, action, message);
    }

    public static void warn(Logger log, String serviceName, String className, String methodName, String ip, String action, String message) {
        log.warn(LOG_TEMPLATE, serviceName, getTraceId(), sanitizeIp(ip), className, methodName, action, message);
    }

    public static void error(Logger log, String serviceName, String className, String methodName, String ip, String action, String message, Throwable t) {
        log.error(LOG_TEMPLATE, serviceName, getTraceId(), sanitizeIp(ip), className, methodName, action, message, t);
    }
}
