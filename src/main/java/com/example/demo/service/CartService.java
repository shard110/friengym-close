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

    public Cart addOrUpdateCartItem(Cart cart) {
        Cart existingCartItem = cartRepository.findByUserIdAndProductPNum(cart.getUser().getId(), cart.getProduct().getpNum());
        if (existingCartItem != null) {
            existingCartItem.setcCount(existingCartItem.getcCount() + 1);
            return cartRepository.save(existingCartItem);
        } else {
            return cartRepository.save(cart);
        }
    }
    
    public Cart getCartItemById(int cnum) {
        return cartRepository.findById(cnum).orElse(null);
    }

    public void removeCartItem(int cnum) {
        cartRepository.deleteById(cnum);
    }
}
