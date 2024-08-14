package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ponum")
    private Long poNum;

    @Column(name = "username", nullable = false, length = 50)
    private String username; 

    @Column(name = "poTitle", nullable = false, length = 100)
    private String title; 

    @Column(name = "poContents", nullable = false, length = 4000)
    private String content; 

    @Column(name = "poDate", updatable = false)
    private LocalDateTime poDate; 

    @Column(name = "updatedDate")
    private LocalDateTime updatedDate; // 수정 날짜추가

    public Post() {}

    public Post(String title, String content, String username) {
        this.title = title;
        this.content = content;
        this.username = username;
    }

    public Long getPoNum() {
        return poNum;
    }

    public void setPoNum(Long poNum) {
        this.poNum = poNum;
    }

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

    public LocalDateTime getPoDate() {
        return poDate;
    }

    public void setPoDate(LocalDateTime poDate) {
        this.poDate = poDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @PrePersist
    public void onCreate() {
        this.poDate = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}