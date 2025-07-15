// src/main/java/com/example/demo/service/AuthServiceImpl.java
package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        // 1. Cek apakah username sudah ada
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username sudah terdaftar!");
        }

        // 2. Hash password sebelum disimpan
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 3. Simpan data user beserta alamat dan wali
        return userRepository.save(user);
    }

    @Override
    public User loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user; // Login berhasil
            } else {
                throw new IllegalArgumentException("Password salah!");
            }
        } else {
            throw new IllegalArgumentException("Username tidak ditemukan!");
        }
    }

    @Override
    public User getUserProfile(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(null); // Penting: Jangan kirim password kembali ke frontend
            return user;
        } else {
            return null; // Atau throw NoSuchElementException
        }
    }
}