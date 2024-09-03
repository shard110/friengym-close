package com.example.demo.service;

import com.example.demo.entity.Cart;
import com.example.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getCartItems(String userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart addCartItem(Cart cart) {
        try {
            return cartRepository.save(cart);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("카트에 상품 추가 오류..: ", e);
        }
    }

    public void removeCartItem(int cnum) {
        cartRepository.deleteById(cnum);
    }
}
