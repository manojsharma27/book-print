package com.ms.printing.bookprint.controllers;

import com.ms.printing.bookprint.exceptions.BookPrintException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {BookPrintException.class})
    protected ResponseEntity<Object> handleBookPrintException(BookPrintException ex, WebRequest request) {
        LOGGER.error("Exception: {}", ex.getMessage());
        return handleExceptionInternal(ex, ex.getMessage(), getHeaders(), ex.getHttpStatus(), request);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleError(RuntimeException ex, WebRequest request) {
        LOGGER.error("Exception: {}", ex.getMessage());
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Bad Request" + "\n" + ex.getMessage();
        LOGGER.error("Bad Request", ex);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return httpHeaders;
    }
}