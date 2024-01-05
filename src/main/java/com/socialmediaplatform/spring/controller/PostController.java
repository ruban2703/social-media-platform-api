package com.socialmediaplatform.spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socialmediaplatform.spring.model.CommentModel;
import com.socialmediaplatform.spring.model.Post;
import com.socialmediaplatform.spring.service.AuthService;
import com.socialmediaplatform.spring.service.PostService;

import jakarta.validation.Valid;




@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	
    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestHeader(name="Authorization",
    required = false) String authorizationHeader,@Valid @RequestBody Post post) {
    	String userDetails = (authorizationHeader != null) ? extractUserDetails(authorizationHeader) : "DefaultUser";
    	post.setUser(userDetails);
        Post savedPost = postService.createPost(post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<List<Post>>> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        List<List<Post>>  posts = postService.getAllPosts(page, size);
        return new ResponseEntity<List<List<Post>>>(posts, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable Long postId) {
        Post post = postService.getPost(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody Post post) {
        Post updatedPost = postService.updatePost(postId, post);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<Post> addCommentToPost(@PathVariable Long postId, @RequestBody CommentModel comment) {
        Post savedComment = postService.addCommentToPost(postId, comment);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}/likes")
    public ResponseEntity<Void> likePost(@PathVariable Long postId) {
        postService.likePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    private String extractUserDetails(String authorizationHeader) {
        // Extracting user details from JWT
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        System.out.println("Token"+jwtToken);
        
        return AuthService.extractUsername(jwtToken);
    }
    
}