package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "post")  // 데이터베이스 테이블 이름이 'post'인 경우 사용
public class Post {
  @Id
  private int ponum;
  private String id; // 기본 키, 데이터베이스에 맞게 타입 조정 가능
  private String potitle; // 제목
  private String pocontents; // 내용
  private boolean powarning = false; // 경고 여부
  private int viewCnt; // 조회수
  private int replyCnt; // 댓글수
  private Date podate;
  private String pofile;
}
