package com.satheesh.portfolio.controller;

import com.satheesh.common.constants.AppConstants;
import com.satheesh.common.constants.MessageConstants;
import com.satheesh.portfolio.service.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description REST Controller for tracking resume download analytics.
 */
@RestController
@RequestMapping(AppConstants.RESUME_PATH)
@RequiredArgsConstructor
@Tag(name = "Resume API", description = "Endpoints for tracking resume download events and viewing analytics")
public class ResumeController {

    private final ResumeService resumeService;

    /**
     * Tracks a resume download event.
     * 
     * @param request HttpServletRequest for IP, User-Agent, Referer
     * @return ResponseEntity with status message
     */
    @PostMapping("/download")
    @Operation(summary = "Track Resume Download", description = "Records client download metadata for resume analytics")
    public ResponseEntity<Map<String, String>> trackDownload(HttpServletRequest request) {
        String ipAddress = extractClientIp(request);
        String userAgent = request.getHeader("User-Agent");
        String referer = request.getHeader("Referer");

        resumeService.trackDownload(ipAddress, userAgent, referer);
        return ResponseEntity.ok(Map.of("message", MessageConstants.RESUME_DOWNLOAD_SUCCESS));
    }

    /**
     * Retrieves resume download metrics.
     * 
     * @return ResponseEntity with statistics
     */
    @GetMapping("/stats")
    @Operation(summary = "Get Download Statistics", description = "Returns total resume download metrics")
    public ResponseEntity<Map<String, Object>> getDownloadStats() {
        return ResponseEntity.ok(resumeService.getDownloadStats());
    }

    private String extractClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader(AppConstants.HEADER_FORWARDED_FOR);
        if (xfHeader == null || xfHeader.isBlank()) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0].trim();
    }
}
