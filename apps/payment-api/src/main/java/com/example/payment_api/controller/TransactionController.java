package com.example.payment_api.controller;

import com.example.payment_api.dto.transaction.CreateTransactionRequest;
import com.example.payment_api.dto.transaction.CreateTransactionResponse;
import com.example.payment_api.exception.InsufficientBalanceException;
import com.example.payment_api.service.TransactionService;
import com.example.shared_lib.exception.DatabaseException;
import com.example.shared_lib.exception.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping
    public ResponseEntity<CreateTransactionResponse> createTransaction(
            @Valid @RequestBody CreateTransactionRequest request,
            @RequestHeader("Authorization") String token) throws NotFoundException, InsufficientBalanceException, DatabaseException {
        var response = transactionService.makeTransaction(token, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
