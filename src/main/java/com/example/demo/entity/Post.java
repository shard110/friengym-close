package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Transient;
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

    @Column(name = "fileUrl", length = 500) // 파일 URL 컬럼 추가
    private String fileUrl;

 

     @ManyToOne // 다대일 관계를 설정합니다.
    @JoinColumn(name = "id", nullable = false) // 외래키 컬럼 이름
    
    private User user; // 작성자 (User 엔티티와 연결)

    // Add userId field
    @Transient
    private String userId; // 데이터베이스에 저장되지않음
    

   
   // 생성자
   public Post(String poTitle, String poContents, String username, User user) {
    this.poTitle = poTitle;
    this.poContents = poContents;
    this.username = username;
    this.user = user; // User 객체 설정
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