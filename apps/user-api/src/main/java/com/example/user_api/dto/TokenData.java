package com.example.user_api.dto;

import java.util.Map;

public record TokenData(
        String subject,
        Map<String, Object> claims
) {
}
