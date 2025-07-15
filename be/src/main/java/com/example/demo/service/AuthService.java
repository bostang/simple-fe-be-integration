// src/main/java/com/example/demo/service/AuthService.java
package com.example.demo.service;

import com.example.demo.model.User;

public interface AuthService {
    User registerUser(User user);
    User loginUser(String username, String password);
    User getUserProfile(String username);
    // Anda bisa menambahkan method lain jika diperlukan, misal:
    // boolean isUsernameAvailable(String username);
}