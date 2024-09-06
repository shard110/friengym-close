package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;
import com.example.demo.entity.Post;

public class PostRequest {
    private String title;
    private String content;
    private String userId;
    private MultipartFile file;

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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Post toPost() {
        Post post = new Post();
        post.setPoTitle(this.title);
        post.setPoContents(this.content);
        return post;
    }
}
