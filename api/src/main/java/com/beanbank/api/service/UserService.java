package com.beanbank.api.service;

import com.beanbank.api.model.User;
import com.beanbank.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

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
