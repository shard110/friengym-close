package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.service.FileService;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Post> createPost(
        @RequestPart("post") String postJson,
        @RequestPart("userId") String userId,
        @RequestPart(value = "file", required = false) MultipartFile file) {

        Post post;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            post = objectMapper.readValue(postJson, Post.class);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        post.setUserId(userId);

        String fileUrl = null;
        if (file != null && !file.isEmpty()) {
            try {
                fileUrl = fileService.save(file);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }
        post.setFileUrl(fileUrl);

        User user = userService.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        post.setUser(user);

        try {
            Post createdPost = postService.createPost(post, userId);
            return ResponseEntity.ok(createdPost);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


@GetMapping("/{poNum}")
public ResponseEntity<Post> getPostById(@PathVariable("poNum") Integer poNum) {
    Post post = postService.getPostById(poNum); // 조회수 증가 처리
    if (post != null) {
        postService.incrementViewCount(poNum);
        return ResponseEntity.ok(post);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    }

    @PutMapping("/{poNum}")
    public ResponseEntity<Post> updatePost(@PathVariable("poNum") Integer poNum, @RequestBody Post newPostData) {
        Post updatedPost = postService.updatePost(poNum, newPostData);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/post/{poNum}")
    public ResponseEntity<String> deletePost(@PathVariable("poNum") Integer poNum) {
        boolean deleted = postService.deletePost(poNum);
        if (deleted) {
            return ResponseEntity.ok("Post with poNum " + poNum + " has been deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Post with poNum " + poNum + " not found.");
        }
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) {
        try {
            Resource file = fileService.getFile(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (Exception e) {
            System.err.println("Error while retrieving file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


@GetMapping
public ResponseEntity<Map<String, Object>> getPosts(
    @RequestParam(value = "page", defaultValue = "1") int page,
    @RequestParam(value = "size", defaultValue = "10") int size,
    @RequestParam(value = "search", defaultValue = "") String search) { // 검색 파라미터 추가

    Map<String, Object> posts = postService.getPagedPosts(page, size, search); // 검색 파라미터 전달
    return ResponseEntity.ok(posts);
}
}