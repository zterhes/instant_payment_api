package com.example.user_api.controller;

import com.example.user_api.service.JWTService;
import com.example.user_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/dummy-auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        String token = userService.login();
        return ResponseEntity.ok(token);
    }
}
