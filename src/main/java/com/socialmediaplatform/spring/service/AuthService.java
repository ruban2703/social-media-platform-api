package com.socialmediaplatform.spring.service;

import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialmediaplatform.spring.exception.UnauthorizedException;
import com.socialmediaplatform.spring.model.User;
import com.socialmediaplatform.spring.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class AuthService {
    
	//Hard-coding the Secret
    private static final String SECRET = "ahfbdehufvbnasaadcasfdgdhdgthtfhjftdsekhjufvbkshadfbnkvjsbadfkuhgvbahkedgfbvkhazdsfbdsakuhfbnaehigfbnskuhdbngvkushfdbzvkcjhasdbgucvbadgvbckahdsbvkjahdsbvkuhjagb";
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days
    @Autowired
    private UserRepository useRepository;

    public String authenticateUser(User user) {
        // Validate user credentials (using username and password)
        Optional<User> existingUser = useRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent() && existingUser.get().getPassword().equals(user.getPassword())) { 
        	// Generate and return a token 
        	String token = generateToken(existingUser.get().getUsername());
            return "Bearer " + token;
        } else {
            throw new UnauthorizedException("Invalid credentials");
        }
    }
	
    
    public static String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();
    }
    public static String extractUsername(String token) {
        return Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
	
}