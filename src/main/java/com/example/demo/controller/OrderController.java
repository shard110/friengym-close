package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.DorderDTO;
import com.example.demo.entity.Ordertbl;
import com.example.demo.entity.Dorder;
import com.example.demo.service.OrderService;
import com.example.demo.config.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO, @RequestHeader("Authorization") String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("Invalid token");
        }

        String userId = jwtTokenProvider.getClaims(token).getSubject();
        Ordertbl order = new Ordertbl();
        order.setOdate(orderDTO.getOdate());
        order.setId(userId);

        List<Dorder> dorders = orderDTO.getDorders().stream().map(dorderDTO -> {
            Dorder dorder = new Dorder();
            dorder.setpNum(dorderDTO.getpNum());
            dorder.setDoCount(dorderDTO.getDoCount());
            dorder.setDoPrice(dorderDTO.getDoPrice());
            return dorder;
        }).collect(Collectors.toList());

        Ordertbl savedOrder = orderService.createOrder(order, dorders);

        List<DorderDTO> savedDorders = savedOrder.getDorders().stream().map(dorder -> {
            DorderDTO dorderDTO = new DorderDTO();
            dorderDTO.setDoNum(dorder.getDoNum());
            dorderDTO.setOnum(dorder.getOnum());
            dorderDTO.setpNum(dorder.getpNum());
            dorderDTO.setDoCount(dorder.getDoCount());
            dorderDTO.setDoPrice(dorder.getDoPrice());
            return dorderDTO;
        }).collect(Collectors.toList());

        OrderDTO savedOrderDTO = new OrderDTO();
        savedOrderDTO.setOnum(savedOrder.getOnum());
        savedOrderDTO.setOdate(savedOrder.getOdate());
        savedOrderDTO.setId(savedOrder.getId());
        savedOrderDTO.setDorders(savedDorders);

        return savedOrderDTO;
    }
}
