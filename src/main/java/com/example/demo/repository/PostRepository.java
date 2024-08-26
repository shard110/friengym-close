package com.example.demo.repository;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.example.demo.entity.Post;

public interface PostRepository  extends JpaRepository<Post, Integer> {
    @NonNull
    Page<Post> findAll(@NonNull Pageable pageable);
}
