package com.satheesh.portfolio.exception;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Custom runtime exception thrown when duplicate contact form submission is detected (HTTP 400).
 */
public class DuplicateSubmissionException extends RuntimeException {
    public DuplicateSubmissionException(String message) {
        super(message);
    }
}
