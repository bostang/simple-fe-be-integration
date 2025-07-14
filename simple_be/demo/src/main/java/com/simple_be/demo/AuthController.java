package com.simple_be.demo;

// src/main/java/com/example/loginbackend/AuthController.java

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api") // Semua endpoint di controller ini akan diawali dengan /api
@CrossOrigin(origins = "http://localhost:3000") // Penting: Izinkan frontend React Anda
public class AuthController {

    @PostMapping("/login") // Endpoint untuk menerima permintaan login
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Logika sederhana untuk validasi username dan password
        System.out.println("Menerima permintaan login untuk username: " + loginRequest.getUsername());

        if ("user".equals(loginRequest.getUsername()) && "pass".equals(loginRequest.getPassword())) {
            // Jika username dan password cocok
            return ResponseEntity.ok(new MessageResponse("Login berhasil! Selamat datang, " + loginRequest.getUsername() + "!"));
        } else {
            // Jika username atau password salah
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Username atau password salah."));
        }
    }
}