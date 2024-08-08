package com.example.demo.user;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_table") // 테이블 이름을 매핑
public class User {

    @Id
    private String id; // VARCHAR2(50)
    private String pwd; // VARCHAR2(100)
    private String name; // VARCHAR2(10)
    private String phone; // VARCHAR2(30)
    private String sex; // VARCHAR2(10)
    private Integer height; // NUMBER
    private Integer weight; // NUMBER
    private Date birth; // DATE
    private Integer firstday; // NUMBER
    private Integer restday; // NUMBER
    private byte[] photo; // BLOB

    // 기본 생성자
    public User() {}

    // 전체 필드를 포함하는 생성자
    public User(String id, String pwd, String name, String phone, String sex, Integer height, Integer weight, Date birth, Integer firstday, Integer restday, byte[] photo) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.phone = phone;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.birth = birth;
        this.firstday = firstday;
        this.restday = restday;
        this.photo = photo;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Integer getFirstday() {
        return firstday;
    }

    public void setFirstday(Integer firstday) {
        this.firstday = firstday;
    }

    public Integer getRestday() {
        return restday;
    }

    public void setRestday(Integer restday) {
        this.restday = restday;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
