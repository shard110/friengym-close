// UserDto.java

package com.example.demo.dto;

import java.util.Date;

public class UserDto {
    private String id;
    private String pwd;
    private String name;
    private String phone;
    private String sex;
    private Integer height;
    private Integer weight;
    private Date birth;
    private Integer firstday;
    private Integer restday;
    private byte[] photo;

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
