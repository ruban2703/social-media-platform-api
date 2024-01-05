package com.socialmediaplatform.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialmediaplatform.spring.exception.ResourceNotFoundException;
import com.socialmediaplatform.spring.model.User;
import com.socialmediaplatform.spring.repository.UserRepository;



@Service
public class UserService {
    
	@Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        // Validate if the username is unique
        if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
        	return userRepository.save(user);
        	
        }
        else {
        	throw new RuntimeException("Username already exists");
		}
        
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }
}
