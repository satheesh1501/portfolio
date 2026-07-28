package com.satheesh.portfolio.service.impl;

import com.satheesh.common.constants.AppConstants;
import com.satheesh.common.constants.MessageConstants;
import com.satheesh.portfolio.entity.ResumeDownloadEvent;
import com.satheesh.portfolio.repository.ResumeDownloadRepository;
import com.satheesh.portfolio.service.ResumeService;
import com.satheesh.common.util.AppLogger;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Service implementation for resume download analytics.
 */
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private static final Logger log = LoggerFactory.getLogger(ResumeServiceImpl.class);
    private static final String CLASS_NAME = ResumeServiceImpl.class.getSimpleName();

    private final ResumeDownloadRepository repository;

    @Override
    @Transactional
    public void trackDownload(String ipAddress, String userAgent, String referer) {
        String methodName = "trackDownload";
        AppLogger.info(log, "Portfolio-Service", CLASS_NAME, methodName, ipAddress, MessageConstants.LOG_ACTION_TRACK_RESUME,
                "Tracking resume download event");

        ResumeDownloadEvent event = ResumeDownloadEvent.builder()
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .referer(referer)
                .build();

        repository.save(event);
    }

    @Override
    public Map<String, Object> getDownloadStats() {
        String methodName = "getDownloadStats";
        AppLogger.info(log, "Portfolio-Service", CLASS_NAME, methodName, AppConstants.UNKNOWN_IP, MessageConstants.LOG_ACTION_TRACK_RESUME,
                "Calculating resume download metrics");

        long total = repository.count();
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        long last30Days = repository.countByDownloadedAtBetween(thirtyDaysAgo, LocalDateTime.now());

        return Map.of(
                "totalDownloads", total,
                "last30DaysDownloads", last30Days
        );
    }
}
