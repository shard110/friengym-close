package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Ask;
import com.example.demo.repository.AskRepository;

@Service
@Transactional  // 서비스 계층에서 트랜잭션 관리
public class AskService {

    @Autowired
    private AskRepository askRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // PasswordEncoder 주입

    // 모든 문의글 조회 (페이징 처리)
    public Page<Ask> getAllAsks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return askRepository.findAll(pageable);
    }


    // 특정 문의글 조회
    public Ask getAskByIdAndPassword(int anum, String rawPassword) {
        Ask ask = askRepository.findById(anum)
                .orElseThrow(() -> new IllegalArgumentException("문의글을 찾을 수 없습니다."));
      
        if (rawPassword != null && passwordEncoder.matches(rawPassword, ask.getPasswordHash())) {
            return ask;
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    // 새로운 문의글 생성
    public Ask createAsk(Ask ask, String rawPassword) {
        // rawPassword 변수가 매개변수로 전달됩니다.
        String hashedPassword = passwordEncoder.encode(rawPassword);
        ask.setPasswordHash(hashedPassword);
        return askRepository.save(ask);
    }

    // 문의글 수정
    public Ask updateAsk(int anum, String rawPassword, Ask updatedAsk) {
        Ask existingAsk = getAskByIdAndPassword(anum, rawPassword); // 비밀번호 검증
        existingAsk.setATitle(updatedAsk.getATitle());
        existingAsk.setAContents(updatedAsk.getAContents());
        return askRepository.save(existingAsk);
    }

    // 문의글 삭제
    public void deleteAsk(int anum, String rawPassword) {
        Ask ask = getAskByIdAndPassword(anum, rawPassword); // 비밀번호 검증
        askRepository.delete(ask);
    }



    // 특정 작성자의 모든 문의글을 페이징 처리하여 조회
    public Page<Ask> getAsksByUserId(String id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return askRepository.findById(id, pageable);
    }
}
