package com.example.payment_api.service;

import com.example.payment_api.dto.account.CreateAccountRequest;
import com.example.payment_api.model.AccountEntity;
import com.example.payment_api.model.BalanceEntity;
import com.example.payment_api.repository.BalanceRepository;
import com.example.payment_api.repository.AccountRepository;
import com.example.payment_api.service.client.UserApiClient;
import com.example.shared_lib.exception.EntitySaveException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.shared_lib.exception.DatabaseException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BalanceRepository balanceRepository;

    @Autowired
    UserApiClient userApiClient;


    public Integer createAccount(CreateAccountRequest request, String token) throws EntitySaveException, DatabaseException {
        var userId = userApiClient.getUserId(token);
        if (Objects.isNull(request)) {
            return saveAccount(userId, 0);
        } else {
            return saveAccount(userId, request.getInitialBalance());
        }
    }
    @Transactional
    private Integer saveAccount(UUID userId, double balance) throws EntitySaveException, DatabaseException {
        try {
        var accountEntity = AccountEntity.builder().ownerId(userId).createdAt(LocalDateTime.now()).build();
        var account = accountRepository.saveAndFlush(accountEntity);
        var balanceEntity = BalanceEntity.builder().account(account).balance(balance).createdAt(LocalDateTime.now()).build();
        balanceRepository.saveAndFlush(balanceEntity);
        return account.getId();
        } catch (Exception e) {
            log.error("e: ", e);
            if (e instanceof DataIntegrityViolationException) {
                throw new EntitySaveException(e.getMessage());
            }
            throw new DatabaseException(e.getMessage());
        }
    }
}
