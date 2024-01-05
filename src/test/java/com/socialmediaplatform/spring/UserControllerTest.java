package com.bezkoder.spring.jpa.h2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.socialmediaplatform.spring.controller.UserController;
import com.socialmediaplatform.spring.model.User;
import com.socialmediaplatform.spring.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testRegisterUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        ResponseEntity<User> response = userController.registerUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService, times(1)).registerUser(user);
    }

    @Test
    public void testGetUserProfile() {
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setUsername("testUser");

        when(userService.getUser(userId)).thenReturn(mockUser);

        ResponseEntity<User> response = userController.getUserProfile(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());
        verify(userService, times(1)).getUser(userId);
    }
}
