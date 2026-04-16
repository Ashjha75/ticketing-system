package com.ashish.ticketing.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BusinessException {
    public NotFoundException(String message) {
        super(message, "NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}

