package com.satheesh.portfolio.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom validation constraint to block HTML tags and JavaScript injection
 * in user-submitted input fields.
 *
 * Protects against:
 *  - XSS: <script>alert('xss')</script>
 *  - HTML injection: <img onerror="...">
 *  - JS protocol: javascript:void(0)
 *  - Inline event handlers: onclick="...", onerror="..."
 */
@Documented
@Constraint(validatedBy = NoHtmlValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoHtml {
    String message() default "HTML tags and script content are not allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
