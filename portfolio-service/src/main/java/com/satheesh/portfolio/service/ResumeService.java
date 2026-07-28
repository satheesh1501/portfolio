package com.satheesh.portfolio.service;

import java.util.Map;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Service interface for tracking and reporting resume download analytics.
 */
public interface ResumeService {

    /**
     * Records a resume download event with client metadata.
     * 
     * @param ipAddress Client IP address
     * @param userAgent Client User-Agent string
     * @param referer Client Referer URL
     */
    void trackDownload(String ipAddress, String userAgent, String referer);

    /**
     * Retrieves overall resume download statistics.
     * 
     * @return Map containing total count and 30-day count
     */
    Map<String, Object> getDownloadStats();
}
