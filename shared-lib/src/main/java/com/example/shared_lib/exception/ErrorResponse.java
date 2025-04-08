package com.example.shared_lib.exception;


import java.time.LocalDateTime;

public record ErrorResponse(
        String code,
        int status,
        LocalDateTime timestamp,
        String message
) {
}
