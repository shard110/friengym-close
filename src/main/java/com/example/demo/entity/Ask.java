package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ask")  // 데이터베이스 테이블 이름이 'ask'인 경우 사용
public class Ask {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 증가 설정
  private int anum;
  private String id;
  private String aTitle; // 제목
  private String aContents; // 내용
  private Date aDate;
  private String afile;
  private String passwordHash; // 비밀번호 해시값
}
