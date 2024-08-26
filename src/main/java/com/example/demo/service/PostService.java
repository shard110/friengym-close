package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PostDao;
import com.example.demo.dto.Page;
import com.example.demo.entity.Post;
import com.example.demo.repository.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostDao postDao;

    // JPA를 사용한 페이지네이션
    public List<Post> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return postRepository.findAll(pageable).getContent();
    }

    // 총 게시글 수 가져오기 (JPA)
    public long getTotalPostsCount() {
        return postRepository.count();
    }

    // 복잡한 쿼리나 특정 작업을 위해 JDBC 사용
    public List<Post> getCustomPosts(int page, int size) {
        Page pageRequest = new Page(page, size);
        return postDao.getPosts(pageRequest);
    }
}