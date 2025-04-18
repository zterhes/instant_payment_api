package com.example.user_api.service;

import com.example.shared_lib.exception.DatabaseException;
import com.example.shared_lib.exception.NotFoundException;
import com.example.user_api.exception.TokenGeneratorException;
import com.example.user_api.model.LoginEntity;
import com.example.user_api.model.UserEntity;
import com.example.user_api.repository.LoginRepository;
import com.example.user_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public String login() throws DatabaseException, TokenGeneratorException {
        try {
            var user = userRepository.saveAndFlush(UserEntity.builder().createdAt(LocalDateTime.now()).build());
            String token = jwtService.generateToken(user.getId());
            var loginEntity = LoginEntity.builder().createdAt(LocalDateTime.now()).userId(user.getId()).token(token).build();
            loginRepository.saveAndFlush(loginEntity);
            return token;
        } catch (Exception e) {
            if (e instanceof TokenGeneratorException) {
                throw (TokenGeneratorException) e;
            } else {
                throw new DatabaseException(e.getMessage());
            }
        }
    }

    public UUID getUserId(String token) {
        var tokenData = jwtService.parseToken(token);
        var userId = UUID.fromString(tokenData.subject());
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"))
                .getId();
    }

}
