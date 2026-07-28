package com.satheesh.common.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Unit tests for NoHtmlValidator verifying XSS attack rejection.
 */
class NoHtmlValidatorTest {

    private NoHtmlValidator validator;

    @BeforeEach
    void setUp() {
        validator = new NoHtmlValidator();
    }

    @Test
    @DisplayName("Should accept valid clean text without HTML tags")
    void testValidCleanText() {
        assertTrue(validator.isValid("Hello Satheesh, great portfolio!", null));
        assertTrue(validator.isValid("Looking forward to connecting regarding Java 21 roles.", null));
    }

    @Test
    @DisplayName("Should accept null or blank values (deferred to @NotBlank)")
    void testNullOrBlankText() {
        assertTrue(validator.isValid(null, null));
        assertTrue(validator.isValid("", null));
        assertTrue(validator.isValid("   ", null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "<script>alert('xss')</script>",
            "<img src='x' onerror='alert(1)'>",
            "javascript:void(0)",
            "<iframe src='http://malicious.com'></iframe>",
            "<object data='evil.swf'></object>",
            "<button onclick='stealCookies()'>Click</button>",
            "<a href='javascript:alert(1)'>Link</a>"
    })
    @DisplayName("Should reject strings containing XSS or HTML injection attack vectors")
    void testRejectsXssVectors(String maliciousInput) {
        assertFalse(validator.isValid(maliciousInput, null), "Failed to block: " + maliciousInput);
    }
}
