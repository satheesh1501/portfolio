package com.satheesh.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Shared custom validation constraint to block HTML tags and JavaScript injection (XSS).
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
