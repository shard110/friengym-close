package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Post;
import com.example.demo.service.PostService;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    @Autowired
    private PostService postService;

    // Create
    @PostMapping("/post")
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    // 모든 게시글 (페이지네이션 지원)
    @GetMapping("/posts")
    public Map<String, Object> getPosts(
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {
        return postService.getPagedPosts(page, size);
    }

    // 상세 페이지
    @GetMapping("/post/{poNum}")
    public Post getPostById(@PathVariable("poNum") Integer poNum) {
        return postService.getPostById(poNum);
    }

    // 업데이트
    @PutMapping("/post/{poNum}")
    public Post updatePost(@PathVariable("poNum") Integer poNum, @RequestBody Post newPostData) {
        return postService.updatePost(poNum, newPostData);
    }

    // 삭제
    @DeleteMapping("/post/{poNum}")
    public String deletePost(@PathVariable("poNum") Integer poNum) {
        postService.deletePost(poNum);
        return "Post with poNum " + poNum + " has been deleted successfully.";
    }
}