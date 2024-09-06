package com.example.demo.repository;

import com.example.demo.entity.Ordertbl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Ordertbl, Integer> {
}
