package com.socialmediaplatform.spring.model;


import java.util.ArrayList;
import java.util.List;

//import javax.persistence.ManyToOne;


//import javax.persistence.*;
import jakarta.persistence.*;

@Entity
//@Data
//@NoArgsConstructor
@Table(name = "app_post")
public class Post {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "content")
    private String content;

    //@ManyToOne
    //@JoinColumn(name = "user")
    @Column(name = "username_post")
    private String user;
    
//    @Column(name = "comments")
//    private List<Comment> comments;
    @Column(name = "comments")
    private List<String> comments;
    
    @Column(name = "likes")
    private long likes;
    
    
	public Post() {
		this.user=null;
		this.comments=null;
		this.likes=0;
	}

	public Post(Long id, String content, String user) {
		this.id = id;
		this.content = content;
		this.user = user;
	}

//	public void addCommentToList(Comment newString) {
//        if (comments == null) {
//        	comments = new ArrayList<>();
//        }
//        comments.add(newString);
//    }
//	
//	public List<Comment> getComments() {
//		return comments;
//	}
//
//	public void setComments(List<Comment> comments) {
//		this.comments = comments;
//	}
	
	public void addCommentToList(String newString) {
        if (comments == null) {
        	comments = new ArrayList<>();
        }
        comments.add(newString);
    }
	
	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public long getLikes() {
		return likes;
	}

	public void setLikes() {
		this.likes = likes+1;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

    
}
