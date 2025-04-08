package com.example.payment_api.service;

import com.example.payment_api.dto.transaction.CreateTransactionRequest;
import com.example.payment_api.dto.transaction.CreateTransactionResponse;
import com.example.payment_api.exception.ClientCallException;
import com.example.payment_api.exception.InsufficientBalanceException;
import com.example.payment_api.model.AccountEntity;
import com.example.payment_api.model.TransactionEntity;
import com.example.payment_api.repository.AccountRepository;
import com.example.payment_api.repository.TransactionsRepository;
import com.example.payment_api.service.client.UserApiClient;
import com.example.shared_lib.exception.DatabaseException;
import com.example.shared_lib.exception.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Log4j2
@Service
public class TransactionService {

    @Autowired
    UserApiClient userApiClient;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionsRepository transactionsRepository;

    @Transactional
    public CreateTransactionResponse makeTransaction(String token, CreateTransactionRequest request) throws NotFoundException, DatabaseException, InsufficientBalanceException {
        try {
            var userId = userApiClient.getUserId(token);
            AccountEntity ownerAccount = accountRepository.findByOwnerId(userId);

            if (Objects.isNull(ownerAccount)) {
                throw new NotFoundException("Sender account not found");
            }

            var balance = ownerAccount.getBalance().getBalance();
            if (balance < request.getAmount()) {
                throw new InsufficientBalanceException("Account balance not enough");
            }

            ownerAccount.getBalance().setBalance(balance - request.getAmount());
            accountRepository.saveAndFlush(ownerAccount);

            AccountEntity recipientAccount = accountRepository.findByOwnerId(request.getRecipient());
            if (Objects.isNull(recipientAccount)) {
                throw new NotFoundException("Recipient account not found");
            }
            recipientAccount.getBalance().setBalance(recipientAccount.getBalance().getBalance() + request.getAmount());
            accountRepository.saveAndFlush(recipientAccount);

            TransactionEntity transactionEntity = TransactionEntity.builder()
                    .sender(ownerAccount)
                    .recipient(recipientAccount)
                    .amount(request.getAmount())
                    .createdAt(LocalDateTime.now())
                    .build();

            var transaction = transactionsRepository.saveAndFlush(transactionEntity);

            return new CreateTransactionResponse(transaction.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
            if (!(e instanceof NotFoundException) && !(e instanceof InsufficientBalanceException) && !(e instanceof ClientCallException)) {
                throw new DatabaseException(e.getMessage());
            } else {
                throw e;
            }

        }
    }

}
