package com.spring.studentproject.service.impl;

import com.spring.studentproject.dto.login.LoginRequest;
import com.spring.studentproject.dto.login.LoginResponse;
import com.spring.studentproject.model.UserEntity;
import com.spring.studentproject.repository.UserRepository;
import com.spring.studentproject.service.LoginService;
import com.spring.studentproject.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;

    @Value("${jwt.expiryInMinutes}")
    private Integer expiryInMin;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Override
    public LoginResponse login(LoginRequest request) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(request.getUsername());
        if (userEntity.isPresent())
            if (userEntity.get().getPassword().equals(request.getPassword())) {
                Date expiry = new Date(System.currentTimeMillis() + (expiryInMin * 60 * 1000));
                return new LoginResponse(JwtUtil.generateToken(userEntity.get().getUsername(), expiry, secretKey), expiry);
            }
        throw new RuntimeException("Invalid username or password");
    }
}
