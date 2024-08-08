// UserController.java
package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.example.demo.security.JwtProvider;
import com.example.demo.user.User;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findUserByEmail(loginRequest.getEmail());
        
        if (user != null && user.getPwd().equals(loginRequest.getPassword())) {
            // User 객체를 UserDetailsImpl로 변환
            UserDetailsImpl userDetails = new UserDetailsImpl(user);
            // 로그인 성공 시 JWT 생성
            String token = jwtProvider.generateToken(userDetails);
            return ResponseEntity.ok(new LoginResponse(token));
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDto userDto) {
        User newUser = new User(
            userDto.getId(), userDto.getPwd(), userDto.getName(), userDto.getPhone(),
            userDto.getSex(), userDto.getHeight(), userDto.getWeight(), userDto.getBirth(),
            userDto.getFirstday(), userDto.getRestday(), userDto.getPhoto()
        );
        userService.saveUser(newUser); // Assuming saveUser method is implemented in UserService
        return ResponseEntity.ok("User registered successfully");
    }
}
