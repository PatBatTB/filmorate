package com.github.patbattb.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception{
    public NotFoundException(String message) {
        super(message);
        log.warn(String.format("\"%s\" in (%s)", message, this.getStackTrace()[0]));
    }
}
