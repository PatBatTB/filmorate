package com.github.patbattb.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FilmAlreadyExist extends Exception {
    public FilmAlreadyExist(String message) {
        super(message);
        log.info(message);
    }
}
