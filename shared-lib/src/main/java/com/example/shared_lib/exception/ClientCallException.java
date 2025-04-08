package com.example.shared_lib.exception;

public class ClientCallException extends RuntimeException {
    public ClientCallException(String message) {
        super(message);
    }
}
