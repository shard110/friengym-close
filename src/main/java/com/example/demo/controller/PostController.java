package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.entity.Post;
import com.example.demo.service.FileService;
import com.example.demo.service.PostService;

import java.util.Map;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:3000") // CORS 설정: 클라이언트가 로컬 호스트에서 요청을 보낼 수 있도록 허용
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    /**
     * 게시글을 생성하고, 파일을 업로드합니다.
     * 
     * @param post 게시글 정보
     * @param userId 사용자 ID
     * @param file 업로드할 파일 (선택 사항)
     * @return 생성된 게시글
     */
    @PostMapping
    public ResponseEntity<Post> createPost(
        @RequestPart("post") Post post, // 게시글 정보
        @RequestPart("userId") String userId, // 사용자 ID
        @RequestPart(value = "file", required = false) MultipartFile file) { // 선택적 파일

        String fileUrl = null;
        // 파일이 존재하고 비어있지 않은 경우, 파일을 저장합니다.
        if (file != null && !file.isEmpty()) {
            try {
                fileUrl = fileService.save(file); // 파일 저장 후 URL 반환
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(null); // 파일 저장 실패 시 서버 에러 응답
            }
        }
        post.setFileUrl(fileUrl); // 파일 URL을 게시글에 설정
        post.setUserId(userId); // 사용자 ID 설정
       
        Post createdPost = postService.createPost(post, userId); // 게시글 생성
        return ResponseEntity.ok(createdPost); // 생성된 게시글 반환
    }

    /**
     * 게시글 ID로 게시글을 조회합니다.
     * 
     * @param poNum 게시글 ID
     * @return 게시글 정보
     */
    @GetMapping("/{poNum}")
    public ResponseEntity<Post> getPostById(@PathVariable("poNum") Integer poNum) {
        Post post = postService.getPostById(poNum); // 게시글 조회
        if (post != null) {
            return ResponseEntity.ok(post); // 게시글이 존재하면 반환
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 게시글이 없으면 404 응답
        }
    }

    /**
     * 게시글을 업데이트합니다.
     * 
     * @param poNum 게시글 ID
     * @param newPostData 새로운 게시글 데이터
     * @return 업데이트된 게시글
     */
    @PutMapping("/{poNum}")
    public ResponseEntity<Post> updatePost(@PathVariable("poNum") Integer poNum, @RequestBody Post newPostData) {
        Post updatedPost = postService.updatePost(poNum, newPostData); // 게시글 업데이트
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost); // 업데이트된 게시글 반환
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 게시글이 없으면 404 응답
        }
    }

    /**
     * 게시글을 삭제합니다.
     * 
     * @param poNum 게시글 ID
     * @return 삭제 성공 메시지
     */
    @DeleteMapping("/post/{poNum}")
    public ResponseEntity<String> deletePost(@PathVariable("poNum") Integer poNum) {
        boolean deleted = postService.deletePost(poNum); // 게시글 삭제 및 결과 확인
        if (deleted) {
            return ResponseEntity.ok("Post with poNum " + poNum + " has been deleted successfully."); // 성공 응답
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Post with poNum " + poNum + " not found."); // 게시글이 없으면 404 응답
        }
    }

    /**
     * 파일을 다운로드합니다.
     * 
     * @param filename 파일 이름
     * @return 파일 리소스
     */
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) {
        try {
            Resource file = fileService.getFile(filename); // 파일 조회
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file); // 파일 다운로드 응답
                } catch (Exception e) {
                    System.err.println("Error while retrieving file: " + e.getMessage());
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 파일이 없으면 404 응답
                }
            }

    /**
     * 페이지네이션을 지원하여 게시글 목록을 조회합니다.
     * 
     * @param page 페이지 번호
     * @param size 페이지당 게시글 수
     * @return 게시글 목록
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getPosts(
        @RequestParam(value = "page", defaultValue = "1") int page, // 페이지 번호
        @RequestParam(value = "size", defaultValue = "10") int size) { // 페이지당 게시글 수

        Map<String, Object> posts = postService.getPagedPosts(page, size); // 게시글 목록 조회
        return ResponseEntity.ok(posts); // 게시글 목록 반환
    }
}