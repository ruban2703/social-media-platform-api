package com.bezkoder.spring.jpa.h2;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.socialmediaplatform.spring.controller.PostController;
import com.socialmediaplatform.spring.controller.UserController;
import com.socialmediaplatform.spring.exception.ResourceNotFoundException;
import com.socialmediaplatform.spring.model.CommentModel;
import com.socialmediaplatform.spring.model.Post;
import com.socialmediaplatform.spring.model.User;
import com.socialmediaplatform.spring.repository.UserRepository;
import com.socialmediaplatform.spring.service.PostService;
import com.socialmediaplatform.spring.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    
    @InjectMocks
    private UserController userController;
    
    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @Test
    public void testCreatePost() {
        Post post = new Post();
        post.setContent("Test post content");

        ResponseEntity<Post> response = postController.createPost("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaWtlIiwiZXhwIjoxNzA1MjcxODk0fQ.us3eGWP8ZwMzKA1Q1n-hXjEehQI0sob2Z5oMZL6BxUGkbkM8iV5CgCGtGHZxvnsUmu_sSQ4OPqKKZiWohyPbhg",post);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(postService, times(1)).createPost(post);
    }

    @Test
    public void testGetAllPosts() {
        int page = 0;
        int size = 10;
        List<Post> mockPosts = Arrays.asList(new Post(), new Post());
        List<List<Post>> mockPostsList= Arrays.asList(mockPosts);
        when(postService.getAllPosts(page, size)).thenReturn(mockPostsList);

        ResponseEntity<List<List<Post>>> response = postController.getAllPosts(page, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPosts, response.getBody());
        verify(postService, times(1)).getAllPosts(page, size);
    }

    @Test
    public void testGetPostById() {
        Long postId = 1L;
        Post mockPost = new Post();
        mockPost.setId(postId);
        mockPost.setContent("Test post content");

        when(postService.getPost(postId)).thenReturn(mockPost);

        ResponseEntity<Post> response = postController.getPost(postId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPost, response.getBody());
        verify(postService, times(1)).getPost(postId);
    }

    @Test
    public void testUpdatePost() {
        Long postId = 1L;
        Post updatedPost = new Post();
        updatedPost.setContent("Updated post content");

        ResponseEntity<Post> response = postController.updatePost(postId, updatedPost);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(postService, times(1)).updatePost(postId, updatedPost);
    }

    @Test
    public void testDeletePost() {
        Long postId = 1L;

        ResponseEntity<Void> response = postController.deletePost(postId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(postService, times(1)).deletePost(postId);
    }

    @Test
    public void testAddCommentToPost() {
        Long postId = 1L;
        CommentModel comment = new CommentModel();
        comment.setComment("Test comment content");

        ResponseEntity<Post> response = postController.addCommentToPost(postId, comment);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(postService, times(1)).addCommentToPost(postId, comment);
    }

    @Test
    public void testLikePost() {
        Long postId = 1L;

        ResponseEntity<Void> response = postController.likePost(postId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(postService, times(1)).likePost(postId);
    }
    
    @Test
    public void testGetUserById() {
        // Arrange
        long userId = 1L;
        User mockUser = new User(userId, "testuser", "password");

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        User result = userService.getUser(userId);

        // Assert
        assertEquals(mockUser, result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserByIdNotFound() {
        // Arrange
        long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.getUser(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testCreateUser() {
        // Arrange
        User newUser = new User(null, "newuser", "password");

        when(userRepository.save(any(User.class))).thenReturn(newUser);

        // Act
        User result = userService.registerUser(newUser);

        // Assert
        assertEquals(newUser, result);
        verify(userRepository, times(1)).save(newUser);
    }

    // Add more tests for other methods in UserService

}

