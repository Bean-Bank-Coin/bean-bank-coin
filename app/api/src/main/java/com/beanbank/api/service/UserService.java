package com.beanbank.api.service;

import com.beanbank.api.repository.UserRepository;
import com.models.User;
import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User findUser(int id) {
        Optional<User> foundUser = userRepository.findById(id);

        if (foundUser.isPresent()) {
            return foundUser.get();
        }

        return null;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
