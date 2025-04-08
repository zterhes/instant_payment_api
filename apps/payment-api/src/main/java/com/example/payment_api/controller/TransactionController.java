package com.example.payment_api.controller;

import com.example.payment_api.dto.transaction.CreateTransactionRequest;
import com.example.payment_api.dto.transaction.CreateTransactionResponse;
import com.example.payment_api.exception.InsufficientBalanceException;
import com.example.payment_api.service.TransactionService;
import com.example.shared_lib.dto.GetTransactionResponse;
import com.example.shared_lib.exception.DatabaseException;
import com.example.shared_lib.exception.ErrorResponse;
import com.example.shared_lib.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Operation(summary = "Create an transaction", description = "Create an transaction and return the transaction id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created", content = @Content(schema = @Schema(implementation = CreateTransactionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error codes and descriptions are available in the shared-lib error codes enum", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<CreateTransactionResponse> createTransaction(
            @Parameter(required = true, description = "Request body", content = @Content(schema = @Schema(implementation = CreateTransactionRequest.class)))
            @Valid @RequestBody CreateTransactionRequest request,

            @Parameter(required = true, description = "Authorization jwt token")
            @RequestHeader("Authorization") String token) throws NotFoundException, InsufficientBalanceException, DatabaseException {
        var response = transactionService.makeTransaction(token, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<GetTransactionResponse> getTransaction(@RequestParam("id") String id) {
        return ResponseEntity.ok(transactionService.getTransaction(id));
    }

}
