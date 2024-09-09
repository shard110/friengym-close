package com.example.demo.dto;

import com.example.demo.entity.Post;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponse {
    private int poNum;
    private String poTitle;
    private String poContents;
    private String createdDate;
    private String modifiedDate;
    private int viewCnt;
    private String name;
    private List<CommentResponse> comments;
    private int commentCount;

    // 기존 생성자
    public PostResponse(Post post) {
        this.poNum = post.getPoNum();
        this.poTitle = post.getPoTitle();
        this.poContents = post.getPoContents();
        this.createdDate = post.getPoDate() != null ? 
                           post.getPoDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) : "No Date";
        this.modifiedDate = post.getUpdatedDate() != null ? 
                            post.getUpdatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) : "No Date";
        this.viewCnt = post.getViewCnt();
        this.name = post.getUser() != null ? post.getUser().getName() : "Unknown";
        this.comments = post.getComments() != null ? 
                        post.getComments().stream()
                            .map(CommentResponse::new)
                            .collect(Collectors.toList()) 
                        : Collections.emptyList();
        this.commentCount = this.comments.size(); // 댓글 수 설정
    }

    // 댓글 수를 포함하는 생성자
    public PostResponse(Post post, int commentCount) {
        this.poNum = post.getPoNum();
        this.poTitle = post.getPoTitle();
        this.poContents = post.getPoContents();
        this.createdDate = post.getPoDate() != null ? 
                           post.getPoDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")) : "No Date";
        this.modifiedDate = post.getUpdatedDate() != null ? 
                            post.getUpdatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")) : "No Date";
        this.viewCnt = post.getViewCnt();
        this.name = post.getUser() != null ? post.getUser().getName() : "Unknown";
        this.comments = post.getComments() != null ? 
                        post.getComments().stream()
                            .map(CommentResponse::new)
                            .collect(Collectors.toList()) 
                        : Collections.emptyList();
        this.commentCount = commentCount; // 댓글 수 설정
    }
}