package com.ms.printing.bookprint.exceptions;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends BookPrintException {

    public DataNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause, HttpStatus.NOT_FOUND);
    }

}
