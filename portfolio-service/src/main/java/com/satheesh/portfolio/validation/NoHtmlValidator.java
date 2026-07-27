package com.satheesh.portfolio.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Validator implementation for the @NoHtml annotation.
 *
 * Rejects any input that contains:
 *  - HTML tags: <script>, <img>, <iframe>, <object>, any <tag>
 *  - JavaScript protocol: javascript:
 *  - Inline event handlers: onclick=, onerror=, onload=, etc.
 *
 * Returns true (valid) for null/blank values — @NotBlank handles those.
 */
public class NoHtmlValidator implements ConstraintValidator<NoHtml, String> {

    private static final Pattern UNSAFE_PATTERN = Pattern.compile(
            "<[^>]*>" +                         // Any HTML tag: <script>, <img>, etc.
            "|javascript\\s*:" +                // javascript: protocol
            "|on\\w+\\s*=" +                    // Event handlers: onclick=, onerror=, etc.
            "|<\\s*script" +                    // <script (with optional whitespace)
            "|<\\s*iframe" +                    // <iframe
            "|<\\s*object",                     // <object
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // Defer null/blank check to @NotBlank
        }
        return !UNSAFE_PATTERN.matcher(value).find();
    }
}
