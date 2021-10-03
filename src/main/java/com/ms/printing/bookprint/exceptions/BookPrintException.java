package com.ms.printing.bookprint.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BookPrintException extends RuntimeException {

    private HttpStatus httpStatus;

    public BookPrintException(String message) {
        super(message);
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public BookPrintException(String message, Throwable cause) {
        super(message, cause);
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public BookPrintException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public BookPrintException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }
}
