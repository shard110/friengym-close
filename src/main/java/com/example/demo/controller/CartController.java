package com.example.demo.controller;

import com.example.demo.entity.Cart;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public List<Cart> getCartItems(@PathVariable String userId) {
        return cartService.getCartItems(userId);
    }

    @PostMapping
    public Cart addCartItem(@RequestBody Cart cart) {
        return cartService.addCartItem(cart);
    }

    @DeleteMapping("/{cnum}")
    public void removeCartItem(@PathVariable int cnum) {
        cartService.removeCartItem(cnum);
    }
}
