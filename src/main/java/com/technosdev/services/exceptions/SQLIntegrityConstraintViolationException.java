package com.technosdev.services.exceptions;

public class SQLIntegrityConstraintViolationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SQLIntegrityConstraintViolationException(String message) {
        super(message);
    }
}
