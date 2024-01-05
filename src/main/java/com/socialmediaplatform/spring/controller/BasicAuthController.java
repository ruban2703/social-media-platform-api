package com.socialmediaplatform.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialmediaplatform.spring.model.User;
import com.socialmediaplatform.spring.service.AuthService;



@RestController
@RequestMapping("/api/auth")
public class BasicAuthController {
    
    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<String> authenticateUser(@RequestBody User user) {
        String token = authService.authenticateUser(user);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}