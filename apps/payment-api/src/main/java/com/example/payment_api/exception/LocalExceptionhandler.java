package com.example.payment_api.exception;

import com.example.shared_lib.exception.ErrorResponse;
import com.example.shared_lib.exception.ExceptionCodes;
import com.example.shared_lib.exception.GlobalExceptionHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@ControllerAdvice
public class LocalExceptionhandler extends GlobalExceptionHandler {

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalanceException(InsufficientBalanceException ex) {
        log.error(ex.getMessage());
        var errorResponse = new ErrorResponse(ExceptionCodes.INSUFFICIENT_BALANCE.getCode(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
