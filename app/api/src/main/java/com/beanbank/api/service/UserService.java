package com.beanbank.api.service;

import com.models.User;
import com.beanbank.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    final
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteUser(int userID) {
        userRepository.deleteById(userID);
    }

    @Transactional
    public User updateUser(int userID, User userInfo) {
        User currentUser = userRepository.findById(userID).get();
        currentUser.setUsername(userInfo.getUsername());
        currentUser.setPassword(userInfo.getPassword());
        currentUser.setEmail(userInfo.getEmail());
        return currentUser;
    }
}