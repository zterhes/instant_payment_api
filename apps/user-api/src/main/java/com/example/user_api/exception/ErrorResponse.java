package com.example.user_api.exception;


import java.time.LocalDateTime;

public record ErrorResponse(
        String code,
        int status,
        LocalDateTime timestamp
) {
}
