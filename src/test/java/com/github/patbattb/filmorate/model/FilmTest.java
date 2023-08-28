package com.github.patbattb.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class FilmTest {

    private Validator validator;
    private static final String TITLE = "title";
    private static final String BLANK_STRING = "";
    private static final String DESCRIPTION_10_CHARS = "descript10";
    private static final LocalDate CORRECT_DATE = LocalDate.of(2020, 1, 1);
    private static final LocalDate TOO_EARLIER_DATE = LocalDate.of(1500, 1, 1);
    private static final LocalDate BORDER_EARLIER_DATE = LocalDate.of(1894, 12, 31);
    private static final LocalDate BORDER_LATER_DATE = LocalDate.of(1895, 1, 1);
    private static final Duration POSITIVE_DURATION = Duration.ofHours(2);
    private static final Duration ZERO_DURATION = Duration.ZERO;
    private static final Duration NEGATIVE_DURATION = Duration.ofHours(-2);

    @BeforeEach
    void setUp() {
        try (ValidatorFactory vf = Validation.buildDefaultValidatorFactory()) {
            validator = vf.getValidator();
        }
    }

    private List<ConstraintViolation<Film>> getConstraintList(Set<ConstraintViolation<Film>> set) {
        return new ArrayList<>(set);
    }

    @Test
    @DisplayName("Test film constructor with the correct data.")
    void shouldFilmWithCorrectDataGivesNoViolations() {
        var film = new Film(TITLE, DESCRIPTION_10_CHARS,CORRECT_DATE, POSITIVE_DURATION);
        var validate = getConstraintList(validator.validate(film));
        assertThat(validate.isEmpty()).isEqualTo(true);
    }

    @Test
    @DisplayName("Test film constructor with title == null")
    void shouldFilmWithNullTitleGivesViolation() {
        String errorMessage = "must not be blank";
        var film = new Film(null, DESCRIPTION_10_CHARS, CORRECT_DATE, POSITIVE_DURATION);
        var validate = getConstraintList(validator.validate(film));
        assertThat(validate.get(0).getMessage()).isEqualTo(errorMessage);
    }

    @Test
    @DisplayName("Test film constructor with the blank title.")
    void shouldFilmWithBlankTitleGivesViolation() {
        String errorMessage = "must not be blank";
        var film = new Film(BLANK_STRING, DESCRIPTION_10_CHARS,CORRECT_DATE,POSITIVE_DURATION);
        var validate = getConstraintList(validator.validate(film));
        assertThat(validate.get(0).getMessage()).isEqualTo(errorMessage);
    }

    @Test
    @DisplayName("Test film constructor with the blank description")
    void shouldFilmWithBlankDescriptionGivesNoViolations() {
        var film = new Film(TITLE, BLANK_STRING, CORRECT_DATE, POSITIVE_DURATION);
        var validate = getConstraintList(validator.validate(film));
        assertThat(validate.isEmpty()).isEqualTo(true);
    }

    @Test
    @DisplayName("Test film constructor with description size 200 chars")
    void shouldFilmWith200CharsDescriptionGivesNoViolations() {
        String description = DESCRIPTION_10_CHARS.repeat(20);
        var film = new Film(TITLE, description, CORRECT_DATE, POSITIVE_DURATION);
        var validate = getConstraintList(validator.validate(film));
        assertThat(validate.isEmpty()).isEqualTo(true);
    }

    @Test
    @DisplayName("Test film constructor with description size 201 chars")
    void shouldFilmWith201CharsDescriptionGivesViolation() {
        String errorMessage = "size must be between 0 and 200";
        String description = DESCRIPTION_10_CHARS.repeat(20) +
                "a";
        var film = new Film(TITLE, description, CORRECT_DATE, POSITIVE_DURATION);
        var validate = getConstraintList(validator.validate(film));
        assertThat(validate.get(0).getMessage()).isEqualTo(errorMessage);
    }

    @Test
    @DisplayName("Test film constructor with description size 300 chars")
    void shouldFilmWith300CharsDescriptionGivesViolation() {
        String errorMessage = "size must be between 0 and 200";
        String description = DESCRIPTION_10_CHARS.repeat(30);
        var film = new Film(TITLE, description, CORRECT_DATE, POSITIVE_DURATION);
        var validate = getConstraintList(validator.validate(film));
        assertThat(validate.get(0).getMessage()).isEqualTo(errorMessage);
    }

    @Test
    @DisplayName("Test film constructor with too earlier date.")
    void shouldFilmWithTooEarlierDateGivesViolation() {
        String errorMessage = "The date must be later then 1894-12-31";
        var film = new Film(TITLE, DESCRIPTION_10_CHARS, TOO_EARLIER_DATE, POSITIVE_DURATION);
        var validate = getConstraintList(validator.validate(film));
        assertThat(validate.get(0).getMessage()).isEqualTo(errorMessage);
    }

    @Test
    @DisplayName("Test film constructor with lower boundary value.")
    void shouldFilmWithLowerBoundaryValueGivesViolation() {
        String errorMessage = "The date must be later then 1894-12-31";
        var film = new Film(TITLE, DESCRIPTION_10_CHARS, BORDER_EARLIER_DATE, POSITIVE_DURATION);
        var validate = getConstraintList(validator.validate(film));
        assertThat(validate.get(0).getMessage()).isEqualTo(errorMessage);
    }

    @Test
    @DisplayName("Test film constructor with upper boundary value.")
    void shouldFilmWithUpperBoundaryValueGivesNoViolations() {
        var film = new Film(TITLE, DESCRIPTION_10_CHARS, BORDER_LATER_DATE, POSITIVE_DURATION);
        var validate = getConstraintList(validator.validate(film));
        assertThat(validate.isEmpty()).isEqualTo(true);
    }

    @Test
    @DisplayName("Test film constructor with negative duration.")
    void shouldFilmWithNegativeDurationGivesViolation() {
        String errorMessage = "The value of the duration must be positive.";
        var film = new Film(TITLE, DESCRIPTION_10_CHARS, CORRECT_DATE, NEGATIVE_DURATION);
        var validate = getConstraintList(validator.validate(film));
        assertThat(validate.get(0).getMessage()).isEqualTo(errorMessage);
    }

    @Test
    @DisplayName("Test film constructor with zero duration")
    void shouldFilmWithZeroDurationGivesViolation() {
        String errorMessage = "The value of the duration must be positive.";
        var film = new Film(TITLE, DESCRIPTION_10_CHARS, CORRECT_DATE, ZERO_DURATION);
        var validate = getConstraintList(validator.validate(film));
        assertThat(validate.get(0).getMessage()).isEqualTo(errorMessage);
    }

}