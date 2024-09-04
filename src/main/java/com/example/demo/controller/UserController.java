package com.example.demo.controller;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.config.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
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

    @GetMapping("/check-id/{id}")
    public ResponseEntity<?> checkId(@PathVariable String id) {
        boolean exists = userService.findById(id).isPresent();
        return ResponseEntity.ok(!exists);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userService.authenticateUser(loginRequest.getId(), loginRequest.getPwd());
        if (user.isPresent()) {
            String token = jwtTokenProvider.createToken(user.get().getId());
            LoginResponse loginResponse = new LoginResponse(token, user.get());
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable String id, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        String username = jwtTokenProvider.getClaims(token).getSubject();
        if (!username.equals(id)) {
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
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        String username = jwtTokenProvider.getClaims(token).getSubject();
        Optional<User> user = userService.findById(username);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PutMapping("/user/update")
    public ResponseEntity<?> updateUserInfo(@RequestBody User updatedUser, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");

        // Validate token
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        // Extract user information from token
        String username = jwtTokenProvider.getClaims(token).getSubject();
        Optional<User> userOpt = userService.findById(username);

        if (userOpt.isPresent()) {
            User existingUser = userOpt.get();

            // Update fields
            existingUser.setName(updatedUser.getName());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setSex(updatedUser.getSex());
            existingUser.setHeight(updatedUser.getHeight());
            existingUser.setWeight(updatedUser.getWeight());
            existingUser.setBirth(updatedUser.getBirth());
            existingUser.setFirstday(updatedUser.getFirstday());
            existingUser.setRestday(updatedUser.getRestday());

            // Save updated user
            User updated = userService.save(existingUser);

            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }
        token = token.substring(7); // Remove "Bearer " prefix

        // Add token to blacklist
        jwtTokenProvider.blacklistToken(token);

        return ResponseEntity.ok("Logout successful");
    }

    @PutMapping("/user/update-photo")
    public ResponseEntity<?> updateProfilePhoto(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        String username = jwtTokenProvider.getClaims(token).getSubject();
        Optional<User> userOpt = userService.findById(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            try {
                // Save file to server
                String fileName = UUID.randomUUID() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
                Path filePath = Paths.get("uploads/" + fileName);
                Files.copy(file.getInputStream(), filePath);

                // Update user with the new photo URL
                user.setPhoto("/api/user/photo/" + fileName);
                userService.save(user);

                return ResponseEntity.ok(user);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save photo");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @GetMapping("/user/photo/{filename}")
    public ResponseEntity<Resource> getProfilePhoto(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("uploads/" + filename);
            Resource resource = new UrlResource(filePath.toUri());

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
