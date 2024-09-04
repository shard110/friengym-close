package com.example.demo.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Product {
  @Id
  private int pNum;
  private String pName;
  private Date pDate;
  private int pCount;
  private int pPrice;
  private String pImg;
  private String pIntro;
  private String pImgUrl;
  private String pDetailImgUrl;
  private int pCate;
  
  @ManyToOne
  @JoinColumn(name = "pcate", referencedColumnName = "catenum", insertable = false, updatable = false)
  private Category category;
  
  public int getpNum() {
    return this.pNum;
  }

  public void setpNum(int pNum) {
    this.pNum = pNum;
  }

  public String getpName() {
    return this.pName;
  }

  public void setpName(String pName) {
    this.pName = pName;
  }

  public Date getpDate() {
    return this.pDate;
  }

  public void setpDate(Date pDate) {
    this.pDate = pDate;
  }

  public int getpCount() {
    return this.pCount;
  }

  public void setpCount(int pCount) {
    this.pCount = pCount;
  }

  public int getpPrice() {
    return this.pPrice;
  }

  public void setpPrice(int pPrice) {
    this.pPrice = pPrice;
  }

  public String getpImg() {
    return this.pImg;
  }

  public void setpImg(String pImg) {
    this.pImg = pImg;
  }

  public String getpIntro() {
    return this.pIntro;
  }

  public void setpIntro(String pIntro) {
    this.pIntro = pIntro;
  }

  public String getpImgUrl() {
    return this.pImgUrl;
  }

  public void setpImgUrl(String pImgUrl) {
    this.pImgUrl = pImgUrl;
  }

  public String getpDetailImgUrl() {
    return this.pDetailImgUrl;
  }

  public void setpDetailImgUrl(String pDetailImgUrl) {
    this.pDetailImgUrl = pDetailImgUrl;
  }

  public int getpCate() {
    return this.pCate;
  }

  public void setpCate(int pCate) {
    this.pCate = pCate;
  }
}
