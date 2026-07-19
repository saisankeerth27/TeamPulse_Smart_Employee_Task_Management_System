package com.teampulse.backend.service;

import com.teampulse.backend.dto.ApiResponse;
import com.teampulse.backend.dto.RegisterRequest;

public interface AuthService {

    ApiResponse register(RegisterRequest request);

}