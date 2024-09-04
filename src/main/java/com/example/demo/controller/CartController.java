package com.example.demo.controller;

import com.example.demo.entity.Cart;
import com.example.demo.entity.User; // User 클래스 임포트
import com.example.demo.service.CartService;
import com.example.demo.config.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/{userId}")
    public List<Cart> getCartItems(@PathVariable String userId, @RequestHeader("Authorization") String token) {
        if (jwtTokenProvider.validateToken(token)) {
            return cartService.getCartItems(userId);
        } else {
            throw new RuntimeException("Invalid token");
        }
    }

    @PostMapping
    public Cart addCartItem(@RequestBody Cart cart, @RequestHeader("Authorization") String token) {
        String userId = jwtTokenProvider.getClaims(token).getSubject();
        if (jwtTokenProvider.validateToken(token)) {
            User user = new User();
            user.setId(userId);
            cart.setUser(user); // User 객체 대신 userId만 설정
            return cartService.addCartItem(cart);
        } else {
            throw new RuntimeException("Invalid token");
        }
    }
    

    @DeleteMapping("/{cnum}")
    public void removeCartItem(@PathVariable int cnum, @RequestHeader("Authorization") String token) {
        String userId = jwtTokenProvider.getClaims(token).getSubject();
        if (jwtTokenProvider.validateToken(token)) {
            cartService.removeCartItem(cnum);
        } else {
            throw new RuntimeException("Invalid token");
        }
    }
}
