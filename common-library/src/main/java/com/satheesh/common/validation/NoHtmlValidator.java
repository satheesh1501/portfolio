package com.satheesh.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Shared validator implementation for @NoHtml constraint annotation.
 */
public class NoHtmlValidator implements ConstraintValidator<NoHtml, String> {

    private static final Pattern UNSAFE_PATTERN = Pattern.compile(
            "<[^>]*>" +
            "|javascript\\s*:" +
            "|on\\w+\\s*=" +
            "|<\\s*script" +
            "|<\\s*iframe" +
            "|<\\s*object",
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return !UNSAFE_PATTERN.matcher(value).find();
    }
}
