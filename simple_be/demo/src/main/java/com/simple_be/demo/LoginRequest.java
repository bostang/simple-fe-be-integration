package com.simple_be.demo;

// src/main/java/com/simple_be/demo/LoginRequest.java


public class LoginRequest {
    private String username;
    private String password;

    // Konstruktor kosong (diperlukan oleh Spring untuk deserialisasi JSON)
    public LoginRequest() {
    }

    // Konstruktor dengan parameter (opsional, untuk kemudahan inisialisasi)
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
               "username='" + username + '\'' +
               ", password='[PROTECTED]'" + // Hindari menampilkan password di log
               '}';
    }
}