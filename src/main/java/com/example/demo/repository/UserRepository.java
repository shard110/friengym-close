package com.example.demo.repository;


import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByName(String name);
        // 이메일로 사용자 조회
    User findByEmail(String email);
}