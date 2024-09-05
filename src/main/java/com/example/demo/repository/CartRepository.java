package com.example.demo.repository;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.entity.Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUserId(String userId);
    CartItemDTO findByUserIdAndProductPNum(String userId, int productPNum);
}