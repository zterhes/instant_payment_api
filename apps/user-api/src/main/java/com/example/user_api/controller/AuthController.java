package com.example.user_api.controller;

import com.example.user_api.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/dummy-auth")
public class AuthController {

    @Autowired
    private JWTService jwtService;

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        UUID uuid = UUID.randomUUID();
        String token = jwtService.generateToken(uuid);
        return ResponseEntity.ok(token);
    }
}
