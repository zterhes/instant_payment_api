package com.example.shared_lib.exception;

import lombok.Getter;

import javax.validation.Validation;

@Getter
public enum ExceptionCodes {
    NOT_FOUND("1001", "not found"),
    TOKEN_GENERATION_ERROR("1002", "Error generating token"),
    DATABASE_ERROR("1003", "Unexpected database error"),
    RUNTIME_ERROR("1004", "Unexpected runtime error"),
    ENTITY_SAVE_ERROR("1005", "Error saving entity"),
    VALIDATION_ERROR("1006", "Validation error"),
    INSUFFICIENT_BALANCE("1007", "Insufficient balance");

    private final String code;
    private final String message;

    ExceptionCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

}