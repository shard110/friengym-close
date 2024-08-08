package com.example.demo.dto;

public class LoginResponse {
    private String token;

    // Constructor with parameters
    public LoginResponse(String token) {
        this.token = token;
    }

    // Default constructor
    public LoginResponse() {}

    // Getter and Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
