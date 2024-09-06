package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.PageDTO; // DTO 클래스 import
import com.example.demo.dto.PostRequest;
import com.example.demo.entity.Post;
import com.example.demo.exception.PostNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.entity.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // JWT 토큰에서 사용자 ID 추출
    public String getUserIdFromToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }
        return jwtTokenProvider.getClaims(token).getSubject();
    }

    public Post createPost(PostRequest postRequest, MultipartFile file) {
        String userId = postRequest.getUserId();

        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID must not be null or empty");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Post post = postRequest.toPost(user);

        String fileUrl = null;
        if (file != null && !file.isEmpty()) {
            try {
                fileUrl = fileService.save(file);
            } catch (Exception e) {
                throw new RuntimeException("File save failed", e);
            }
        }
        post.setFileUrl(fileUrl);
        return postRepository.save(post);
    }

    public Post getPostById(Integer poNum) {
        incrementViewCount(poNum);
        return postRepository.findById(poNum)
                .orElseThrow(() -> new PostNotFoundException(poNum));
    }

    public void incrementViewCount(Integer poNum) {
        Post post = postRepository.findById(poNum)
                .orElseThrow(() -> new PostNotFoundException(poNum));
        post.setViewCnt(post.getViewCnt() + 1);
        postRepository.save(post);
    }

    //게시글 업데이트
  public Post updatePost(Integer poNum, PostRequest postRequest, MultipartFile file, String authHeader) {
    String userId = getUserIdFromToken(authHeader);
    Post post = postRepository.findById(poNum)
            .orElseThrow(() -> new PostNotFoundException(poNum));
    
    if (!post.getUser().getId().equals(userId)) {
        throw new IllegalArgumentException("User not authorized to update this post");
    }


    post.setPoTitle(postRequest.getTitle());
    post.setPoContents(postRequest.getContent());

    if (file != null && !file.isEmpty()) {
        try {
            String fileUrl = fileService.save(file);
            post.setFileUrl(fileUrl);
        } catch (Exception e) {
            throw new RuntimeException("File save failed", e);
        }
    }
    
    return postRepository.save(post);
}

    public void deletePost(Integer poNum, String authHeader) {
        String userId = getUserIdFromToken(authHeader);
        Post post = postRepository.findById(poNum)
                .orElseThrow(() -> new PostNotFoundException(poNum));

        if (!post.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User not authorized to delete this post");
        }

        postRepository.deleteById(poNum);
    }

    public Map<String, Object> getPagedPosts(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("poDate")));
        Page<Post> postPage;

        if (search == null || search.isEmpty()) {
            postPage = postRepository.findAll(pageable);
        } else {
            postPage = postRepository.findByPoTitleContainingIgnoreCase(search, pageable);
        }

        PageDTO pageInfo = new PageDTO(
                page,
                size,
                10, // 노출할 페이지 수
                (int) postPage.getTotalElements());

        Map<String, Object> response = new HashMap<>();
        response.put("pageInfo", pageInfo);
        response.put("posts", postPage.getContent());

        return response;
    }
}
