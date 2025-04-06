package com.example.user_api.repository;

import com.example.user_api.model.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<LoginEntity, Integer> {
}
