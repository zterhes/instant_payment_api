package com.example.payment_api.repository;

import com.example.payment_api.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<TransactionEntity, Integer> {
}
