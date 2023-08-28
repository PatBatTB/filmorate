package com.github.patbattb.filmorate.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class AfterThenValidator
        implements ConstraintValidator<AfterThen, LocalDate> {

    LocalDate constraintLocalDate;

    @Override
    public void initialize(AfterThen constraintAnnotation) {
        constraintLocalDate = LocalDate.parse(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext context) {
        boolean result = localDate.isAfter(constraintLocalDate);
        if (!result) log.warn("The date must be later then {}", constraintLocalDate);
        return result;
    }
}