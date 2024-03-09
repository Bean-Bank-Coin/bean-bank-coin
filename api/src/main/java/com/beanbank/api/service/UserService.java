package com.beanbank.api.service;

import com.beanbank.api.model.User;
import com.beanbank.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void deleteUser(int userID) {
        userRepository.deleteById((long) userID);
    }

    @Transactional
    public User updateUser(int userID, User userInfo) {
        User currentUser = userRepository.findById((long) userID).get();
        currentUser.setUsername(userInfo.getUsername());
        currentUser.setPassword(userInfo.getPassword());
        currentUser.setEmail(userInfo.getEmail());
        return currentUser;
    }
}
