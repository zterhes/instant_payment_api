package com.example.payment_api.exception;

public class ClientCallException extends RuntimeException {
    public ClientCallException(String message) {
        super(message);
    }
}
