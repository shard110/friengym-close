package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.dto.PostRequest;
import com.example.demo.entity.Post;
import com.example.demo.service.FileService;
import com.example.demo.service.PostService;
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

            String userId = postService.getUserIdFromToken(authHeader);
            postRequest.setUserId(userId);

            Post createdPost = postService.createPost(postRequest, file);
            return ResponseEntity.ok(createdPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 게시글 조회 (조회수 증가)
    @GetMapping("/{poNum}")
    public ResponseEntity<Post> getPostById(@PathVariable("poNum") Integer poNum) {
        try {
            Post post = postService.getPostById(poNum);
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 게시글 수정
    @PutMapping("/{poNum}")
    public ResponseEntity<Post> updatePost(
            @PathVariable("poNum") Integer poNum,
            @RequestPart("post") String postJson,
            @RequestHeader("Authorization") String authHeader,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            PostRequest postRequest = objectMapper.readValue(postJson, PostRequest.class);
            postRequest.setUserId(postService.getUserIdFromToken(authHeader)); // User ID 설정

            // 게시글 업데이트
            Post updatedPost = postService.updatePost(poNum, postRequest, file, authHeader);
            return ResponseEntity.ok(updatedPost);
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 게시글 삭제
    @DeleteMapping("/{poNum}")
    public ResponseEntity<String> deletePost(
            @PathVariable("poNum") Integer poNum,
            @RequestHeader("Authorization") String authHeader) {
        try {
            postService.deletePost(poNum, authHeader);
            return ResponseEntity.ok("Post with ID " + poNum + " has been deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to delete this post.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the post.");
        }
    }

    // 파일 다운로드
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) {
        try {
            Resource file = fileService.getFile(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 게시글 목록 조회 (페이징 및 검색)
    @GetMapping
    public ResponseEntity<Map<String, Object>> getPosts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "search", defaultValue = "") String search) {

        Map<String, Object> posts = postService.getPagedPosts(page, size, search);
        return ResponseEntity.ok(posts);
    }
}