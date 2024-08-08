package com.example.demo.service;

import com.example.demo.user.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email); // UserRepository에 정의된 메서드를 호출합니다.
    }

    @Override
    public User findUserById(String userId) {
        return userRepository.findById(userId).orElse(null); // UserRepository에서 ID로 사용자 찾기
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user); // UserRepository를 사용하여 사용자 저장
    }
}
