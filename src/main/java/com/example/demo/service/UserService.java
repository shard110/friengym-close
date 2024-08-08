// UserService.java

package com.example.demo.service;

import com.example.demo.user.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User findUserByEmail(String email);
    User findUserById(String userId);
    void saveUser(User user); // Add this method
}
