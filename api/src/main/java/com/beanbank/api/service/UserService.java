package com.beanbank.api.service;

import com.beanbank.api.model.User;
import com.beanbank.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteUser(Long userID) {
        userRepository.deleteById(userID);
    }

    @Transactional
    public User updateUser(Long userID, User userInfo) {
        User currentUser = userRepository.findById(userID).get();
        currentUser.setUsername(currentUser.getUsername());
        currentUser.setPassword(currentUser.getPassword());
        currentUser.setEmail(currentUser.getEmail());
        return currentUser;
    }
}
