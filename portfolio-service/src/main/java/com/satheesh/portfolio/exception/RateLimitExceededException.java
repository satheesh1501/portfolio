package com.satheesh.portfolio.exception;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Custom runtime exception thrown when a client exceeds allowed rate limits (HTTP 429).
 */
public class RateLimitExceededException extends RuntimeException {
    public RateLimitExceededException(String message) {
        super(message);
    }
}
