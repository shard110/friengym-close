package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User registerUser(User user) {
        user.setPwd(encoder.encode(user.getPwd())); // 비밀번호 암호화
        return userRepository.save(user);
    }

    public Optional<User> authenticateUser(String id, String pwd) {
        Optional<User> user = userRepository.findById(id);
        return user.isPresent() && encoder.matches(pwd, user.get().getPwd()) ? user : Optional.empty();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }
}
