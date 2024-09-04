package com.example.demo.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ponum") // 데이터베이스 컬럼 이름
    private Integer poNum; // 게시글 번호

    @Column(name = "username", nullable = false, length = 50)
    private String username;    /////////////////////수정

    @Column(name = "poTitle", nullable = false, length = 100)
    private String poTitle;

    @Column(name = "poContents", nullable = false, length = 4000)
    private String poContents;

    @Column(name = "poDate", updatable = false)
    private LocalDateTime poDate;

    @Column(name = "updatedDate")
    private LocalDateTime updatedDate; // 수정 날짜 추가

    @Column(name = "viewcnt", nullable = false)
    private int viewCnt = 0; // 기본값 설정

    @Column(name = "replycnt", nullable = false)
    private int replyCnt = 0;

 

    // 생성자
    public Post(String poTitle, String poContent, String username) {
        this.poTitle = poTitle;
        this.poContents = poContent;
        this.username = username;
        this.poDate = LocalDateTime.now();
        this.replyCnt = 0; // 기본값 설정
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