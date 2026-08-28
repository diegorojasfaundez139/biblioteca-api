package com.diego.biblioteca.exception;

public class LoanNotFoundException extends RuntimeException {

    public LoanNotFoundException(String message) {
        super(message);
    }
}