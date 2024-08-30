// package com.example.demo.entity;


// import jakarta.persistence.*;
// import lombok.Data;

// import java.io.Serializable;

// import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

// @Data
// @Entity
// @Table(name = "cart")
// public class Cart implements Serializable {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private int cnum;

//     private int cCount;

//     @ManyToOne
//     @JoinColumn(name = "id")
//     private User user;

//     @ManyToOne
//     @JoinColumn(name = "pnum")
//     private Product product;
// }
