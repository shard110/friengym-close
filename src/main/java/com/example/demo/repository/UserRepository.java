package com.example.demo.repository;

<<<<<<< HEAD
import com.example.demo.model.User;
=======
import com.example.demo.entity.User;
>>>>>>> temp-branch
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findById(String id);
}
