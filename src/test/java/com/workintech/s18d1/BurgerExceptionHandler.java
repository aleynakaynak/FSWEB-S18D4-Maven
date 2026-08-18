package com.workintech.s18d1.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class BurgerExceptionHandler {

    @ExceptionHandler(BurgerException.class)
    public ResponseEntity<BurgerErrorResponse> handleException(BurgerException exception) {
        log.error("BurgerException error: {}", exception.getMessage());
        BurgerErrorResponse errorResponse = new BurgerErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse, exception.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BurgerErrorResponse> handleException(Exception exception) {
        log.error("Global exception error: {}", exception.getMessage());
        BurgerErrorResponse errorResponse = new BurgerErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse, org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
