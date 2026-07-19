package com.teampulse.backend.service;

import com.teampulse.backend.dto.ApiResponse;
import com.teampulse.backend.dto.LoginRequest;
import com.teampulse.backend.dto.LoginResponse;
import com.teampulse.backend.dto.RegisterRequest;

public interface AuthService {

    ApiResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}