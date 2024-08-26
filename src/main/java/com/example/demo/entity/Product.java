package com.example.demo.entity;

import java.util.Date;

import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
  @Id
    @Column(name = "pnum")
    @JsonProperty("pNum")
    private int pNum;
    
    @Column(name = "pname")
    @JsonProperty("pName")
    private String pName;

    @Column(name = "pprice")
    @JsonProperty("pPrice")
    private int pPrice;

    @Column(name = "pcount")
    @JsonProperty("pCount")
    private int pCount;

    @Column(name = "pimg")
    @JsonProperty("pImg")
    private String pImg;

    @Column(name = "pdate")
    @JsonProperty("pDate")
    private Date pDate;

    @Column(name = "pintro")
    @JsonProperty("pIntro")
    private String pIntro;

    @ManyToOne
    @JoinColumn(name = "pcate", referencedColumnName = "catenum")
    private Category category;

}
