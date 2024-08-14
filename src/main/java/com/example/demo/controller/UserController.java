package com.example.demo.controller;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.LoginRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        if (registerRequest.getId() == null || registerRequest.getPwd() == null || 
            registerRequest.getName() == null || registerRequest.getPhone() == null || 
            registerRequest.getSex() == null) {
            return ResponseEntity.badRequest().body("All fields are required");
        }
        try {
            User user = new User();
            user.setId(registerRequest.getId());
            user.setPwd(registerRequest.getPwd());
            user.setName(registerRequest.getName());
            user.setPhone(registerRequest.getPhone());
            user.setSex(registerRequest.getSex()); // sex 필드 추가
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userService.authenticateUser(loginRequest.getId(), loginRequest.getPwd());
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // 실제 애플리케이션에서는 세션 또는 토큰을 무효화해야 합니다.
        return ResponseEntity.ok("Logout successful");
    }
}
