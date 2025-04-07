package com.example.payment_api.repository;

import com.example.payment_api.model.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<BalanceEntity, Integer> {
}
