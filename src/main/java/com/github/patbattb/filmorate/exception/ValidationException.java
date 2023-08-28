package com.github.patbattb.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
        log.warn(message);
    }
}
