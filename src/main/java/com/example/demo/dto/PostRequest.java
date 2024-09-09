package com.example.demo.dto;


import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Post;

//주석//
public class PostRequest {

    private Post post;
    private String userId;
    private MultipartFile file; // 파일 추가

    // Getter 및 Setter 정의
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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
}