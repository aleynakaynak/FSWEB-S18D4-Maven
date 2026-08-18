package com.workintech.s18d1.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BurgerExceptionHandler {

    @ExceptionHandler(BurgerException.class)
    public ResponseEntity<BurgerErrorResponse> handleBurgerException(BurgerException e) {
        BurgerErrorResponse errorResponse = new BurgerErrorResponse(e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }
}
