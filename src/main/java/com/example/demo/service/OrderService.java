package com.example.demo.service;

import com.example.demo.entity.Ordertbl;
import com.example.demo.entity.Dorder;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.DorderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DorderRepository dorderRepository;

    public Ordertbl createOrder(Ordertbl order, List<Dorder> dorders) {
        Ordertbl savedOrder = orderRepository.save(order);
        for (Dorder dorder : dorders) {
            dorder.setDoNum(savedOrder.getOnum());
            dorderRepository.save(dorder);
        }
        return savedOrder;
    }
}
