package com.github.patbattb.filmorate.exception;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FilmorateValidationException extends ValidationException {
    public FilmorateValidationException(String message) {
        super(message);
        log.warn(String.format("\"%s\" in (%s)", message, this.getStackTrace()[0]));
    }
}
