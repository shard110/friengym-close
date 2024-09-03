package com.example.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Ask;
import com.example.demo.service.AskService;

@RestController
@RequestMapping("/api/asks")
public class AskController {

  @Autowired
    private AskService askService;

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
    public ResponseEntity<Ask> getAsk(@PathVariable int anum, @RequestParam String password) {
        Ask ask = askService.getAskByIdAndPassword(anum, password); // 수정된 메소드 사용
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
                                        @RequestParam(value = "fileUrl", required = false) String fileUrl){

    Ask ask = new Ask();
    ask.setATitle(title);
    ask.setAContents(contents);

    // 파일 URL 처리
    if (fileUrl != null && !fileUrl.isEmpty()) {
        ask.setAfile(fileUrl); // 파일 URL을 저장
    }

      Ask createdAsk = askService.createAsk(ask, password); // 비밀번호 인수를 추가하여 메소드를 호출합니다.
      return ResponseEntity.ok(createdAsk);
  }
  // 수정 엔드포인트
  @PutMapping("/{anum}")
  public ResponseEntity<Ask> updateAsk(
      @PathVariable int anum,
      @RequestParam String password,
      @RequestBody Ask updatedAsk) {
      
      Ask updated = askService.updateAsk(anum, password, updatedAsk);
      return ResponseEntity.ok(updated);
  }

   // 삭제 엔드포인트
   @DeleteMapping("/{anum}")
   public ResponseEntity<Void> deleteAsk(
       @PathVariable int anum,
       @RequestParam String password) {
       
       askService.deleteAsk(anum, password);
       return ResponseEntity.noContent().build();
   }

   @PostMapping("/check-password")
   public ResponseEntity<Ask> getAskWithPassword(@RequestParam int anum, @RequestParam String password) {
       Ask ask = askService.getAskByIdAndPassword(anum, password);
       return ResponseEntity.ok(ask);
   }
}
