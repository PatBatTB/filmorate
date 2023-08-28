package com.github.patbattb.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private Validator validator;
    private static final String CORRECT_EMAIL = "abcd@gmail.com";
    private static final String INCORRECT_EMAIL = "abcdgmail.com";
    private static final String LOGIN = "login";
    private static final String EMPTY_LOGIN = "";
    private static final String WHITESPACE_LOGIN = "log in";
    private static final String NICKNAME = "nickname";
    private static final String EMPTY_NICKNAME = "";
    private static final LocalDate CORRECT_BIRTHDAY = LocalDate.of(1995, 10, 10);
    private static final LocalDate NOW_BIRTHDAY = LocalDate.now();
    private static final LocalDate FUTURE_BIRTHDAY = LocalDate.now().plus(Period.ofDays(1));





    @BeforeEach
    void setUp() {
        try (ValidatorFactory vf = Validation.buildDefaultValidatorFactory()) {
            validator = vf.getValidator();
        }
    }

    private List<ConstraintViolation<User>> getConstraintList(Set<ConstraintViolation<User>> set) {
        return new ArrayList<>(set);
    }

    @Test
    @DisplayName("Test user constructor with correct data.")
    void shouldUserWithCorrectDataGivesNoViolations() {
        var user = new User(CORRECT_EMAIL, LOGIN, NICKNAME, CORRECT_BIRTHDAY);
        var validate = getConstraintList(validator.validate(user));
        assertThat(validate.isEmpty()).isEqualTo(true);
    }

    @Test
    @DisplayName("Test user constructor with incorrect email.")
    void shouldUserWithIncorrectEmailGivesViolation() {
        String errorMessage = "must be a well-formed email address";
        var user = new User(INCORRECT_EMAIL, LOGIN, NICKNAME, CORRECT_BIRTHDAY);
        var validate = getConstraintList(validator.validate(user));
        assertThat(validate.get(0).getMessage()).isEqualTo(errorMessage);
    }

    @Test
    @DisplayName("Test user constructor with blank login.")
    void shouldUserWithBlankLoginGivesViolation() {
        String errorMessage = "must match \"^\\S+$\"";
        var user = new User(CORRECT_EMAIL, EMPTY_LOGIN, NICKNAME, CORRECT_BIRTHDAY);
        var validate = getConstraintList(validator.validate(user));
        assertThat(validate.get(0).getMessage()).isEqualTo(errorMessage);
    }

    @Test
    @DisplayName("Test user constructor with login with whitespaces.")
    void shouldUserWithWhitespacesInLoginGivesViolation() {
        String errorMessage = "must match \"^\\S+$\"";
        var user = new User(CORRECT_EMAIL, WHITESPACE_LOGIN, NICKNAME, CORRECT_BIRTHDAY);
        var validate = getConstraintList(validator.validate(user));
        assertThat(validate.get(0).getMessage()).isEqualTo(errorMessage);
    }

    @Test
    @DisplayName("Test user constructor with empty nickname.")
    void shouldNicknameEqualsLoginIfUserCreatesWithEmptyNickname() {
        var user = new User(CORRECT_EMAIL, LOGIN, EMPTY_NICKNAME, CORRECT_BIRTHDAY);
        var validate = getConstraintList(validator.validate(user));
        assertThat(validate.isEmpty()).isEqualTo(true);
        assertThat(user.getNickname()).isEqualTo(user.getLogin());
    }

    @Test
    @DisplayName("Test user constructor with present birthday.")
    void shouldUserWithPresentBirthdayGivesViolation() {
        String errorMessage = "must be a past date";
        var user = new User(CORRECT_EMAIL, LOGIN, NICKNAME, NOW_BIRTHDAY);
        var validate = getConstraintList(validator.validate(user));
        assertThat(validate.get(0).getMessage()).isEqualTo(errorMessage);
    }

    @Test
    @DisplayName("Test user constructor with future birthday.")
    void shouldUserWithFutureBirthdayGivesViolation() {
        String errorMessage = "must be a past date";
        var user = new User(CORRECT_EMAIL, LOGIN, NICKNAME, FUTURE_BIRTHDAY);
        var validate = getConstraintList(validator.validate(user));
        assertThat(validate.get(0).getMessage()).isEqualTo(errorMessage);
    }

}