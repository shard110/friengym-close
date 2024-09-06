package com.example.demo.repository;

import com.example.demo.entity.Dorder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DorderRepository extends JpaRepository<Dorder, Integer> {
}
