package com.example.user_api.service;

import com.example.user_api.exception.TokenGeneratorException;
import com.example.user_api.exception.UserServiceDatabaseException;
import com.example.user_api.model.LoginEntity;
import com.example.user_api.model.UserEntity;
import com.example.user_api.repository.LoginRepository;
import com.example.user_api.repository.UserRepository;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String login() throws UserServiceDatabaseException, TokenGeneratorException {
        try {
            var user = userRepository.saveAndFlush(UserEntity.builder().createdAt(LocalDateTime.now()).build());
            String token = jwtService.generateToken(user.getId());
            var loginEntity = LoginEntity.builder().date(LocalDateTime.now()).userId(user.getId()).token(token).build();
            loginRepository.saveAndFlush(loginEntity);
            return token;
        } catch (Exception e) {
            if (e instanceof TokenGeneratorException) {
                throw (TokenGeneratorException) e;
            } else {
                throw new UserServiceDatabaseException(e.getMessage());
            }
        }
    }

}
