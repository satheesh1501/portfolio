package com.satheesh.portfolio.security;

import com.satheesh.common.constants.MessageConstants;
import com.satheesh.portfolio.exception.RateLimitExceededException;
import com.satheesh.common.util.AppLogger;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Redis-backed sliding-window rate limiter service.
 * Uses Redis Sorted Sets (ZSET) to track request timestamps per client IP.
 * Prevents spam and denial-of-service attacks on contact form and AI chatbot.
 */
@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private static final Logger log = LoggerFactory.getLogger(RateLimiterService.class);
    private static final String CLASS_NAME = RateLimiterService.class.getSimpleName();

    private final StringRedisTemplate redisTemplate;

    /**
     * Checks if the request from the given IP exceeds allowed threshold.
     * Throws RateLimitExceededException if limit is exceeded.
     * 
     * @param keyPrefix Redis key prefix (e.g. rate_limit:contact:)
     * @param ip Client IP address
     * @param maxRequests Maximum allowed requests in window
     * @param windowMinutes Window size in minutes
     */
    public void checkRateLimit(String keyPrefix, String ip, int maxRequests, int windowMinutes) {
        String key = keyPrefix + ip;
        long now = Instant.now().toEpochMilli();
        long windowStart = now - TimeUnit.MINUTES.toMillis(windowMinutes);

        try {
            // Remove old entries outside current window
            redisTemplate.opsForZSet().removeRangeByScore(key, 0, windowStart);

            // Count requests in current window
            Long count = redisTemplate.opsForZSet().zCard(key);
            long currentCount = (count != null) ? count : 0;

            if (currentCount >= maxRequests) {
                AppLogger.warn(log, "Portfolio-Service", CLASS_NAME, "checkRateLimit", ip, MessageConstants.LOG_ACTION_RATE_LIMIT,
                        "Rate limit exceeded. Count: " + currentCount + " / Max: " + maxRequests);
                throw new RateLimitExceededException(MessageConstants.RATE_LIMIT_EXCEEDED);
            }

            // Record current request timestamp
            redisTemplate.opsForZSet().add(key, String.valueOf(now), now);
            redisTemplate.expire(key, windowMinutes + 1, TimeUnit.MINUTES);

            AppLogger.info(log, "Portfolio-Service", CLASS_NAME, "checkRateLimit", ip, MessageConstants.LOG_ACTION_RATE_LIMIT,
                    "Rate limit passed. Count: " + (currentCount + 1) + " / Max: " + maxRequests);

        } catch (RateLimitExceededException e) {
            throw e;
        } catch (Exception e) {
            // Fail-open strategy: Log error but allow request if Redis fails
            AppLogger.error(log, "Portfolio-Service", CLASS_NAME, "checkRateLimit", ip, MessageConstants.LOG_ACTION_RATE_LIMIT,
                    "Redis rate limiter encounter failure; failing open", e);
        }
    }
}
