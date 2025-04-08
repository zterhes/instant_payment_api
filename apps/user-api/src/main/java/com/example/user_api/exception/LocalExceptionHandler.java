package com.example.user_api.exception;

import com.example.shared_lib.exception.ExceptionCodes;
import com.example.shared_lib.exception.GlobalExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class LocalExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(TokenGeneratorException.class)
    public ResponseEntity<ErrorResponse> handleTokenGeneratorException(TokenGeneratorException ex) {
        ErrorResponse response = new ErrorResponse( ExceptionCodes.TOKEN_GENERATION_ERROR.getCode(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
