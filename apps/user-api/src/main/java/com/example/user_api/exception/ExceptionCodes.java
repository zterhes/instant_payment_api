package com.example.user_api.exception;

import lombok.Getter;

@Getter
public enum ExceptionCodes {
    USER_NOT_FOUND("1001", "User not found"),
    TOKEN_GENERATION_ERROR("1002", "Error generating token"),
    USER_SERVICE_DATABASE_ERROR("1003", "Database error in user service"),
    RUNTIME_ERROR("1004", "Unexpected runtime error");

    private final String code;
    private final String message;

    ExceptionCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

}