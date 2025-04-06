package com.example.user_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/dummy-auth")
public class AuthController {

    @GetMapping("/login")
    public String login() {
        UUID uuid = UUID.randomUUID();

        return uuid.toString();
    }
}
