package com.satheesh.portfolio.security;

import com.satheesh.common.constants.MessageConstants;
import com.satheesh.portfolio.exception.RateLimitExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Unit tests for RateLimiterService Redis sliding window logic.
 */
@ExtendWith(MockitoExtension.class)
class RateLimiterServiceTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ZSetOperations<String, String> zSetOperations;

    private RateLimiterService rateLimiterService;

    @BeforeEach
    void setUp() {
        rateLimiterService = new RateLimiterService(redisTemplate);
    }

    @Test
    @DisplayName("Should pass rate limit check when request count is within threshold")
    void testRateLimitPasses() {
        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
        when(zSetOperations.zCard(anyString())).thenReturn(2L);

        assertDoesNotThrow(() ->
                rateLimiterService.checkRateLimit("rate_limit:test:", "192.168.1.1", 3, 15)
        );
    }

    @Test
    @DisplayName("Should throw RateLimitExceededException when request count reaches max limit")
    void testRateLimitExceeded() {
        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
        when(zSetOperations.zCard(anyString())).thenReturn(3L);

        assertThrows(RateLimitExceededException.class, () ->
                rateLimiterService.checkRateLimit("rate_limit:test:", "192.168.1.1", 3, 15)
        );
    }
}
