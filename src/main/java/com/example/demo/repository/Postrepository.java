package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

    // 페이지네이션을 위한 메서드
    Page<Post> findAll(Pageable pageable);

    // 제목이나 내용으로 검색할 수 있는 메서드
    Page<Post> findByPoTitleContainingIgnoreCaseOrPoContentsContainingIgnoreCase(
        String title, String content, Pageable pageable);
}