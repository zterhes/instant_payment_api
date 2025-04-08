package com.example.user_api.controller;

import com.example.shared_lib.exception.DatabaseException;
import com.example.shared_lib.exception.ErrorResponse;
import com.example.user_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/dummy-auth")
public class AuthController {

    @Autowired
    private UserService userService;


    @Operation(summary = "Make a new user", description = "Create a new user and return the JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token created"),
            @ApiResponse(responseCode = "500", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/login")
    public ResponseEntity<String> login() throws DatabaseException {
        String token = userService.login();
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Get user id", description = "Get user id from token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is persisted in DB and token is valid", content = @Content(schema = @Schema(implementation = UUID.class))),
            @ApiResponse(responseCode = "500", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/get-user")
    public ResponseEntity<UUID> getUser(@RequestHeader("user-token") String token) {
        return ResponseEntity.ok(userService.getUserId(token));
    }
}
