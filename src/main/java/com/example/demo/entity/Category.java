package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Category {
    @Id
    @Column(name = "catenum")
    @JsonProperty("catenum")
      private int catenum;

    @Column(name = "catename")
    @JsonProperty("catename")
      private String catename;
}