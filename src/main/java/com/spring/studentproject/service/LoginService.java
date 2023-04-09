package com.spring.studentproject.service;

import com.spring.studentproject.dto.login.LoginRequest;
import com.spring.studentproject.dto.login.LoginResponse;

public interface LoginService {

    LoginResponse login(LoginRequest request);
}
