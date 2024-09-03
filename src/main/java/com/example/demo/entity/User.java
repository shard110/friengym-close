package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "usertbl")
public class User {
    @Id
    @NotNull
    private String id;

    @NotNull
    // @Size(min = 6, message = "Password must be at least 6 characters long")
    // @Column(length = 60) // BCrypt는 기본적으로 60자의 길이를 요구합니다.
    private String pwd;

    @NotNull
    private String name;
    
    @NotNull
    private String phone;

    @NotNull
    private String sex;

    private Integer height;
    private Integer weight;
    private Date birth;
    private Integer firstday;
    private Integer restday;
    private byte[] photo;
    private String sessionkey;
    private Date sessionlimit;

}