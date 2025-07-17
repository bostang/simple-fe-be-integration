package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;

public interface AuthService {
    User registerUser(RegisterRequest registerRequest); // Menggunakan RegisterRequest
    User loginUser(LoginRequest loginRequest); // Menggunakan LoginRequest
    User getUserProfile(String email); // Menggunakan email
}