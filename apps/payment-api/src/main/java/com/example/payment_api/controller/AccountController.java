package com.example.payment_api.controller;

import com.example.payment_api.service.AccountService;
import com.example.payment_api.dto.account.CreateAccountRequest;
import com.example.payment_api.dto.account.CreateAccountResponse;
import com.example.shared_lib.exception.DatabaseException;
import com.example.shared_lib.exception.EntitySaveException;
import com.example.shared_lib.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Operation(summary = "Create an account", description = "Create an account with initial balance and return the account id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token created"),
            @ApiResponse(responseCode = "500", description = "Error codes and descriptions are available in the shared-lib error codes enum", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<CreateAccountResponse> createAccount(
            @Parameter(required = true, description = "Authorization jwt token")
            @RequestHeader("Authorization") String token,

            @Parameter(required = false, description = "Request body", content = @Content(schema = @Schema(implementation = CreateAccountRequest.class)))
            @RequestBody(required = false) CreateAccountRequest request) throws EntitySaveException, DatabaseException {
        var accountId = accountService.createAccount(request, token);
        return ResponseEntity.ok(new CreateAccountResponse(accountId));
    }
}
