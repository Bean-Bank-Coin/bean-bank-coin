package com.beanbank.api.repositoryTests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.beanbank.api.repository.UserRepository;
import com.models.User;

@DataJpaTest
class UserRepositoryTests{
    @Autowired
    UserRepository underTest;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldFindByUsername(){
        // Given
        String username = "username";
        User user = new User(
            1, 
            username, 
            "password", 
            "email"
        );
        underTest.save(user);

        // When
        User foundUser = underTest.findByUsername(username);

        // Then
        assertThat(foundUser).isEqualTo(foundUser);
    }

    @Test
    void itShouldfindByEmail(){
        //Given
        String email = "email";
        User user = new User(
            1, 
            "username", 
            "password", 
            email
        );
        underTest.save(user);

        //When
        User foundUser = underTest.findByEmail(email);

        //Then
        assertThat(foundUser).isEqualTo(foundUser);
    }
}