package com.example.demo.dto;


import com.example.demo.entity.Post;
import com.example.demo.entity.User;

public class PostRequest {
    private String title; // Post 엔티티의 poTitle과 매칭
    private String content; // Post 엔티티의 poContents와 매칭
    private String userId; // User 엔티티와 연결

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Post toPost(User user) {
        Post post = new Post();
        post.setPoTitle(this.title);
        post.setPoContents(this.content);
        post.setUser(user);
        return post;
    }
}
