package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.IDENTITY는 데이터베이스에 따라 자동으로 ID를 생성.
    @Column(name = "ponum") // 데이터베이스 열 이름
	private Long poNum;
    
    //임시 (jpa가 기존테이블을 수정할 수있어서 상의 후 바꾸겠습니다) ///////////////
    private String title;
    private String content;
    private String username;
	
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}


    
}

    
