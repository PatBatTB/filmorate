package com.github.patbattb.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@Slf4j
public class AlreadyExistsException extends Exception{
    public AlreadyExistsException(String message) {
        super(message);
        log.warn(message);
    }
}
