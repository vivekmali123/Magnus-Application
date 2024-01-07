package com.jala.ems.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// A custom exception class to handle duplicate entry violation while creating or updating entities in the database
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException(String message) {
        super(message);
    }
}
