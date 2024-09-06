package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.PostRequest;
import com.example.demo.entity.Post;
import com.example.demo.exception.PostNotFoundException;
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
    private UserService userService; // UserService 주입

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private FileService fileService;



    // 게시글 작성
    @PostMapping
    public ResponseEntity<Post> createPost(
            @RequestPart("post") String postJson,
            @RequestHeader("Authorization") String authHeader,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        PostRequest postRequest;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            postRequest = objectMapper.readValue(postJson, PostRequest.class);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // JWT 토큰 검증
        String token = authHeader.replace("Bearer ", "");
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String userId = jwtTokenProvider.getClaims(token).getSubject();
        postRequest.setUserId(userId);
        postRequest.setFile(file);

            // 사용자 존재 확인
    if (!userService.getUserById(userId).isPresent()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    
        try {
            // 게시글 작성 및 반환
            Post createdPost = postService.createPost(postRequest);
            return ResponseEntity.ok(createdPost);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 게시글 조회 (조회수 증가)
    @GetMapping("/{poNum}")
    public ResponseEntity<Post> getPostById(@PathVariable("poNum") Integer poNum) {
        try {
            Post post = postService.getPostById(poNum);
            return ResponseEntity.ok(post);
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 게시글 수정
    @PutMapping("/{poNum}")
    public ResponseEntity<Post> updatePost(
            @PathVariable("poNum") Integer poNum,
            @RequestBody Post newPostData) {
        try {
            // 게시글 업데이트 및 반환
            Post updatedPost = postService.updatePost(poNum, newPostData);
            return ResponseEntity.ok(updatedPost);
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 게시글 삭제
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

    // 파일 다운로드
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) {
        try {
            // 파일을 Resource로 반환
            Resource file = fileService.getFile(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (Exception e) {
            System.err.println("Error while retrieving file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 게시글 목록 조회 (페이징 및 검색)
    @GetMapping
    public ResponseEntity<Map<String, Object>> getPosts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "search", defaultValue = "") String search) { // 검색 파라미터 추가

        // 페이징 및 검색된 게시글 목록 반환
        Map<String, Object> posts = postService.getPagedPosts(page, size, search); // 검색 파라미터 전달
        return ResponseEntity.ok(posts);
    }
}