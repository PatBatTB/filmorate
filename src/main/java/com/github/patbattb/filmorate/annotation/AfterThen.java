package com.github.patbattb.filmorate.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = AfterThenValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
public @interface AfterThen {

    String value() default "1900-01-01";

    String message() default "The date must be later then {value}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}