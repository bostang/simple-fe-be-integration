package com.example.demo.payload.response;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username; // Atau email, id, roles, sesuai kebutuhan

    public JwtResponse(String accessToken, String username) {
        this.token = accessToken;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}