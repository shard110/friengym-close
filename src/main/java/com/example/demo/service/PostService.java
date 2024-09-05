package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PageDTO;    // DTO 클래스 import
import com.example.demo.entity.Post;
import com.example.demo.exception.PostNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.entity.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Post createPost(Post post, String userId) {
        User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId)); // 사용자 정의 예외
        post.setUser(user);
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Integer poNum) {
        return postRepository.findById(poNum)
                .orElseThrow(() -> new PostNotFoundException(poNum));
    }

    public Post updatePost(Integer poNum, Post newPostData) {
        return postRepository.findById(poNum)
                .map(post -> {
                    post.setPoTitle(newPostData.getPoTitle());
                    post.setPoContents(newPostData.getPoContents());
                    return postRepository.save(post);
                })
                .orElseThrow(() -> new PostNotFoundException(poNum));
    }

    public boolean deletePost(Integer poNum) {
        if (!postRepository.existsById(poNum)) {
            return false; // 게시글이 존재하지 않으면 false 반환
        }
        postRepository.deleteById(poNum); // 게시글 삭제
        return true; // 삭제 성공
    }

    public Map<String, Object> getPagedPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Post> postPage = postRepository.findAll(pageable);

        // PageDTO 생성 및 설정
        PageDTO pageInfo = new PageDTO(
                page,
                size,
                10, // 노출할 페이지 수 (예: 10페이지)
                (int) postPage.getTotalElements() // 전체 데이터 수
        );

        // 페이지 정보와 게시글 리스트 포함
        Map<String, Object> response = new HashMap<>();
        response.put("pageInfo", pageInfo);
        response.put("posts", postPage.getContent());

        return response;
    }
}