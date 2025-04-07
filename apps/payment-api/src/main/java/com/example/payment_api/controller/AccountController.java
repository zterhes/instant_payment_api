package com.example.payment_api.controller;

import com.example.payment_api.service.AccountService;
import com.example.payment_api.dto.CreateAccountRequest;
import com.example.payment_api.dto.CreateAccountResponse;
import com.example.shared_lib.exception.DatabaseException;
import com.example.shared_lib.exception.EntitySaveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestHeader("Authorization") String token,
                                                               @RequestBody(required = false) CreateAccountRequest request) throws EntitySaveException, DatabaseException {
        var accountId = accountService.createAccount(request,token);
        return ResponseEntity.ok(new CreateAccountResponse(accountId));
    }
}
