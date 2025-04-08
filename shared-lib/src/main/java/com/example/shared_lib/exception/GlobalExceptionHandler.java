package com.example.shared_lib.exception;

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
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientCallException.class)
    public ResponseEntity<ErrorResponse> handleClientCallException(ClientCallException ex) {
        log.error(ex.getMessage());
        ErrorResponse response = new ErrorResponse(ExceptionCodes.RUNTIME_ERROR.getCode(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        log.error(ex.getMessage());
        ErrorResponse response = new ErrorResponse(ExceptionCodes.RUNTIME_ERROR.getCode(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(),ExceptionCodes.RUNTIME_ERROR.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntitySaveException.class)
    public ResponseEntity<ErrorResponse> handleEntitySaveException(EntitySaveException ex) {
        log.error(ex.getMessage());
        ErrorResponse response = new ErrorResponse(ExceptionCodes.ENTITY_SAVE_ERROR.getCode(), HttpStatus.NOT_MODIFIED.value(), LocalDateTime.now(), ExceptionCodes.ENTITY_SAVE_ERROR.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ErrorResponse> handleServiceDatabaseException(DatabaseException ex) {
        log.error(ex.getMessage());
        ErrorResponse response = new ErrorResponse(ExceptionCodes.DATABASE_ERROR.getCode(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(), ExceptionCodes.DATABASE_ERROR.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        log.error(ex.getMessage());
        ErrorResponse response = new ErrorResponse(ExceptionCodes.NOT_FOUND.getCode(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), ExceptionCodes.NOT_FOUND.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorResponse response = new ErrorResponse(ExceptionCodes.VALIDATION_ERROR.getCode(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), errors.toString());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
