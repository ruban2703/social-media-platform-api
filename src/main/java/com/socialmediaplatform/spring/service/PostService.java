package com.socialmediaplatform.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.socialmediaplatform.spring.exception.ResourceNotFoundException;
import com.socialmediaplatform.spring.model.CommentModel;
import com.socialmediaplatform.spring.model.Post;
import com.socialmediaplatform.spring.repository.PostRepository;



@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public List<List<Post>> getAllPosts(int page, int size) {
 
    	List<List<Post>> allPosts = new ArrayList<>();
        
        while (true) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Post> postsPage = postRepository.findAll(pageable);

            if (postsPage.hasContent()) {
                allPosts.add(new ArrayList<>(postsPage.getContent()));
                page++;
            } else {
                break; 
            }
        }

        return allPosts;
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
    }

    public Post updatePost(Long postId, Post post) {
        // Validate if the post exists
    	Post usernameTobeUpdated = getPost(postId);
    	post.setUser(usernameTobeUpdated.getUser());
        getPost(postId);
        post.setId(postId);
        return postRepository.save(post);
    }

    public void deletePost(Long postId) {
        // Validate if the post exists
        getPost(postId);
        postRepository.deleteById(postId);
    }

    public Post addCommentToPost(Long postId, CommentModel comment) {
        // Validate if the post exists
    	//Post post = getPost(postId);
    	Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        post.addCommentToList(comment.getComment());
        //post.getComments().add(comment);
        return postRepository.save(post);
    }
    public void likePost(Long postId) {
        // Validate if the post exists
    	Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
    	post.setLikes();
         postRepository.save(post);
        
    }
}
