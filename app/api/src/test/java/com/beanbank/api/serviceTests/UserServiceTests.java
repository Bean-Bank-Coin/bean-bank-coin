package com.beanbank.api.serviceTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.beanbank.api.repository.UserRepository;
import com.beanbank.api.service.UserService;
import com.models.User;


@ExtendWith(MockitoExtension.class)
class UserServiceTests{

    @Mock
    private UserRepository userRepository;
    private UserService underTest;

    @BeforeEach
    void setUp(){
        underTest = new UserService(userRepository);
    }

    @Test
    public void itCanCreateUser() {
        //Given
        User user = new User(
            1, 
            "username", 
            "password", 
            "email"
        );

        //When
        underTest.createUser(user);

        //Then
        ArgumentCaptor<User> accounArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(accounArgumentCaptor.capture());

        User capturedAccount = accounArgumentCaptor.getValue();
        assertThat(capturedAccount).isEqualTo(user);
    }
    
    @Test
    public void itCanFindUserByUsername() {
        //Given
        String username = "username";

        //When
        underTest.findUserByUsername(username);

        //Then
        verify(userRepository).findByUsername(username);
    }

    @Test
    public void itCanfindUserByEmail() {
        //Given
        String email = "email";

        //When
        underTest.findUserByEmail(email);

        //Then
        verify(userRepository).findByEmail(email);
    }

    @Test
    public void itCanDeleteUser() {
        //Given
        int userID = 1;

        //When
        underTest.deleteUser(userID);

        //Then
        verify(userRepository).deleteById(userID);
    }

    @Test
    public void itCanupdateUser() {
        int userID = 1;
        User user = new User(
            userID, 
            "username", 
            "password", 
            "email"
        );

        User userInfo = new User(
            userID, 
            "new user", 
            "new password", 
            "new email"
        );
        
        when(userRepository.findById(userID)).thenReturn(Optional.of(user));

        //When
        underTest.updateUser(userID, userInfo);

        //Then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedAccount = userArgumentCaptor.getValue();
        assertThat(capturedAccount).isEqualTo(userInfo);
    }
}