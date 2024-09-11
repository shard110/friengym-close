package com.example.demo.api;

import java.io.IOException;  // 파일 입출력 예외 처리
import java.nio.file.Files;  // 파일 읽기 및 쓰기
import java.nio.file.Path;  // 파일 경로 설정
import java.nio.file.Paths;  // 경로 생성
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.JwtAuthentication;
import com.example.demo.entity.Ask;
import com.example.demo.entity.User;
import com.example.demo.service.AskService;
import com.example.demo.service.UserService;



@RestController
@RequestMapping("/api/asks")
public class AskController {

    @Autowired
    private AskService askService;

    @Autowired
    private UserService userService; // UserService 주입

    @Value("${file.ask-upload-dir}")
    private String askUploadDir;

    
     // 모든 문의글 조회 (페이징 처리)
     @GetMapping
     public ResponseEntity<Page<Ask>> getAllAsks(
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "10") int size) {
         
         Page<Ask> asks = askService.getAllAsks(page, size);  // `page`와 `size`를 전달

         return ResponseEntity.ok(asks);
     }

    // 특정 문의글 조회
    @GetMapping("/{anum}")
    public ResponseEntity<Ask> getAsk(@PathVariable int anum) {
        Ask ask = askService.getAskById(anum); // 수정된 메소드 사용
        return ResponseEntity.ok(ask);
    }

  // 특정 작성자의 모든 문의글을 페이징 처리하여 조회
    @GetMapping("/user/{id}")
    public ResponseEntity<Page<Ask>> getAsksById(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Ask> asks = askService.getAsksByUserId(id, page, size);
        return ResponseEntity.ok(asks);
    }

  // 새로운 문의글 생성
    @PostMapping
    public ResponseEntity<Ask> createAsk(
                                        @RequestParam("aTitle") String title,
                                        @RequestParam("aContents") String contents,
                                        @RequestParam("password") String password,
                                       @RequestParam(value = "afile", required = false) MultipartFile file) {

  // SecurityContextHolder에서 인증 정보 직접 가져오기
    JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
        System.out.println("인증되지 않음.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  // 인증되지 않은 경우 처리
    }
    
    System.out.println("인증된 사용자: " + authentication.getName());
    
    String username = authentication.getName();  // 인증된 사용자 이름 가져오기

    Ask ask = new Ask();
    ask.setATitle(title);
    ask.setAContents(contents);
    ask.setADate(new Timestamp(System.currentTimeMillis())); // 현재 시간으로 Timestamp 설정


// 파일 처리 로직
if (file != null && !file.isEmpty()) {
    try {
       // 파일을 askuploads 폴더에 저장
       String fileName = file.getOriginalFilename();
       Path path = Paths.get(askUploadDir, fileName);
       Files.write(path, file.getBytes());


        ask.setAfile("/uploads/askuploads/" + fileName);  // URL 설정
    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
    
    // 로그인한 사용자 정보를 Ask 엔티티에 설정
    Optional<User> userOpt = userService.getUserById(username);
    if (userOpt.isPresent()) {
        ask.setUser(userOpt.get());
    } else {
        // 유저가 존재하지 않는 경우의 처리 로직을 추가할 수 있습니다.
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

      Ask createdAsk = askService.createAsk(ask, password); // 비밀번호 인수를 추가하여 메소드를 호출합니다.
      return ResponseEntity.ok(createdAsk);
  }
  // 수정 엔드포인트
  @PutMapping("/{anum}")
  public ResponseEntity<Ask> updateAsk(@PathVariable int anum,
                                       @RequestParam("aTitle") String title,
                                       @RequestParam("aContents") String contents,
                                       @RequestParam(value = "afile", required = false) MultipartFile file) {
        Ask ask = askService.getAskById(anum);  // 기존 문의글 가져오기
        ask.setATitle(title);
        ask.setAContents(contents);
    
       // 파일 업데이트 처리
       if (file != null && !file.isEmpty()) {
        try {
          // 파일을 askuploads 폴더에 저장
          String fileName = file.getOriginalFilename();
          Path path = Paths.get(askUploadDir, fileName);
          Files.write(path, file.getBytes());

            ask.setAfile("/uploads/askuploads/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
        Ask updatedAsk = askService.updateAsk(anum, ask);
        return ResponseEntity.ok(updatedAsk);
    }

   // 삭제 엔드포인트
   @DeleteMapping("/{anum}")
public ResponseEntity<Void> deleteAsk(@PathVariable int anum) {
    // anum으로 문의글을 가져옴
    Ask ask = askService.getAskById(anum);  // 해당 문의글을 가져옴
    if (ask.getAfile() != null) {
          // 서버에서 파일 삭제
          try {
        // 절대 경로로 파일 경로 구성
        String filePath = askUploadDir + "/" + Paths.get(ask.getAfile()).getFileName();
        Path path = Paths.get(filePath);
        Files.deleteIfExists(path);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    askService.deleteAsk(anum);
    return ResponseEntity.noContent().build();
}

  @PostMapping("/check-password")
public ResponseEntity<Ask> getAskWithPassword(@RequestBody Map<String, Object> request) {
    int anum = (int) request.get("anum");  // JSON에서 anum 추출
    String password = (String) request.get("password");  // JSON에서 password 추출

    System.out.println("Received anum: " + anum);
    System.out.println("Received password: " + password);

    Ask ask = askService.getAskByIdAndPassword(anum, password);
    return ResponseEntity.ok(ask);
}
}
