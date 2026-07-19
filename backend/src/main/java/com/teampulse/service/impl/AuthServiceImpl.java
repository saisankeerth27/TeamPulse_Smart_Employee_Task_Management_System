package com.teampulse.backend.service.impl;

import org.springframework.stereotype.Service;

import com.teampulse.backend.dto.ApiResponse;
import com.teampulse.backend.dto.RegisterRequest;
import com.teampulse.backend.entity.User;
import com.teampulse.backend.repository.UserRepository;
import com.teampulse.backend.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ApiResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return new ApiResponse(false, "Email already exists");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // We'll encrypt this later
        user.setRole(request.getRole());

        userRepository.save(user);

        return new ApiResponse(true, "User registered successfully");
    }
}