package com.teampulse.backend.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.teampulse.backend.dto.ApiResponse;
import com.teampulse.backend.dto.LoginRequest;
import com.teampulse.backend.dto.LoginResponse;
import com.teampulse.backend.dto.RegisterRequest;
import com.teampulse.backend.entity.User;
import com.teampulse.backend.repository.UserRepository;
import com.teampulse.backend.security.JwtService;
import com.teampulse.backend.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public ApiResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return new ApiResponse(false, "Email already exists");
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);

        return new ApiResponse(true, "User Registered Successfully");
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        Optional<User> optionalUser =
                userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid Email");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid Password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(
                token,
                user.getFullName(),
                user.getEmail(),
                user.getRole().name());
    }

}