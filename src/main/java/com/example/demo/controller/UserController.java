package com.example.demo.controller;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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
            user.setSex(registerRequest.getSex());
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
            String token = jwtTokenProvider.createToken(user.get().getId()); // 실제 토큰 생성
            LoginResponse loginResponse = new LoginResponse(token, user.get());
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable String id, Principal principal) {
        if (principal == null || !principal.getName().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @GetMapping("/mypage")
    public ResponseEntity<?> getUserInfo(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        String userId = principal.getName();
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // 실제 애플리케이션에서는 세션 또는 토큰을 무효화해야 합니다.
        return ResponseEntity.ok("Logout successful");
    }
}
