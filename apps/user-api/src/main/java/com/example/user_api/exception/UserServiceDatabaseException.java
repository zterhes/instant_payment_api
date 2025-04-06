package com.example.user_api.exception;

public class UserServiceDatabaseException extends RuntimeException {
    public UserServiceDatabaseException(String message) {
        super(message);
    }
}
