package com.example.demo.payload.response;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String email; // Diubah dari username menjadi email

    public JwtResponse(String accessToken, String email) { // Menggunakan email
        this.token = accessToken;
        this.email = email;
    }

    // Getters dan Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() { // Mengubah getter untuk email
        return email;
    }

    public void setEmail(String email) { // Mengubah setter untuk email
        this.email = email;
    }
}