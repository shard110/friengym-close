package com.example.demo.service;

<<<<<<< HEAD
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
=======
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> temp-branch
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

<<<<<<< HEAD
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User registerUser(User user) {
        user.setPwd(encoder.encode(user.getPwd())); // 비밀번호 암호화
=======
    public User registerUser(User user) {
        // 비밀번호 암호화 없이 평문 비밀번호를 저장합니다.
>>>>>>> temp-branch
        return userRepository.save(user);
    }

    public Optional<User> authenticateUser(String id, String pwd) {
        Optional<User> user = userRepository.findById(id);
<<<<<<< HEAD
        return user.isPresent() && encoder.matches(pwd, user.get().getPwd()) ? user : Optional.empty();
=======
        return user.isPresent() && pwd.equals(user.get().getPwd()) ? user : Optional.empty();
>>>>>>> temp-branch
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }
}
