package com.github.patbattb.filmorate.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class PositiveDurationValidator implements ConstraintValidator<PositiveDuration, Duration> {
    @Override
    public boolean isValid(Duration duration, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = (!duration.isNegative() && !duration.isZero());
        if (!result) log.warn("The value of the duration must be positive.");
        return result;
    }
}
