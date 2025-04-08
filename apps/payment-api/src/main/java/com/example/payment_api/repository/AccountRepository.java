package com.example.payment_api.repository;

import com.example.payment_api.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    AccountEntity findByOwnerId(UUID ownerId);
}

