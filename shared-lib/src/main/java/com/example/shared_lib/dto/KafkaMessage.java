package com.example.shared_lib.dto;

import java.time.Instant;


public record KafkaMessage(
        String id,
        String content,
        Instant timestamp
) {
    @Override
    public String toString() {
        return String.format("""
        Message[
            id=%s,
            content=%s,
            timestamp=%s
        ]""", id, content, timestamp);
    }
}