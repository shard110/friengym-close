package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.PageDTO;    // DTO 클래스 import
import com.example.demo.dto.PostRequest;
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

    @Autowired
    private FileService fileService;


    public Post createPost(PostRequest postRequest) {
        Post post =  postRequest.toPost(); 
        String userId = postRequest.getUserId();
        MultipartFile file = postRequest.getFile();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        post.setUser(user);

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

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Integer poNum) {
        incrementViewCount(poNum); // 조회수 증가
        return postRepository.findById(poNum)
                .orElseThrow(() -> new PostNotFoundException(poNum));
    }

    public void incrementViewCount(Integer poNum) {
        Post post = postRepository.findById(poNum)
                .orElseThrow(() -> new PostNotFoundException(poNum));
        post.setViewCnt(post.getViewCnt() + 1);
        postRepository.save(post);
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

    public Map<String, Object> getPagedPosts(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("poDate")));
        Page<Post> postPage;

        if (search == null || search.isEmpty()) {
            postPage = postRepository.findAll(pageable);
        } else {
            postPage = postRepository.findByPoTitleContainingIgnoreCase(search, pageable);
        }

        // PageDTO 객체 생성
        PageDTO pageInfo = new PageDTO(
                page,
                size,
                10, // 노출할 페이지 수
                (int) postPage.getTotalElements()
        );

        // 응답 맵 준비
        Map<String, Object> response = new HashMap<>();
        response.put("pageInfo", pageInfo);
        response.put("posts", postPage.getContent());

        return response;
    }
}