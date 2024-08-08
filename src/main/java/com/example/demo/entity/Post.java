package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq_gen")
    @SequenceGenerator(name = "post_seq_gen", sequenceName = "post_seq", allocationSize = 1)
    @Column(name = "ponum")
    private Long postId;

    @Column(name = "id")
    private String userId;

    @Column(name = "poTitle")
    private String title;

    @Column(name = "poDate")
    private LocalDateTime createdDate;

    @Column(name = "poContents", columnDefinition = "TEXT")
    private String content;

    @Column(name = "poFile")
    private String file;

    @Column(name = "powarning")
    private Boolean warning;

    @Column(name = "viewcnt")
    private Integer viewCount;

    @Column(name = "replycnt")
    private Integer replyCount;

    // 기본 생성자
    public Post() {}

    // 모든 필드를 포함한 생성자
    public Post(Long postId, String userId, String title, LocalDateTime createdDate, String content, String file, Boolean warning, Integer viewCount, Integer replyCount) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.createdDate = createdDate;
        this.content = content;
        this.file = file;
        this.warning = warning;
        this.viewCount = viewCount;
        this.replyCount = replyCount;
    }

    // Getter 및 Setter 메서드
    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getFile() { return file; }
    public void setFile(String file) { this.file = file; }

    public Boolean getWarning() { return warning; }
    public void setWarning(Boolean warning) { this.warning = warning; }

    public Integer getViewCount() { return viewCount; }
    public void setViewCount(Integer viewCount) { this.viewCount = viewCount; }

    public Integer getReplyCount() { return replyCount; }
    public void setReplyCount(Integer replyCount) { this.replyCount = replyCount; }
}