package com.eeerrorcode.pilllaw.controller;

import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.dto.letter.LetterRequestDto;
import com.eeerrorcode.pilllaw.dto.letter.LetterResponseDto;
import com.eeerrorcode.pilllaw.entity.follow.Letter;
import com.eeerrorcode.pilllaw.service.letter.LetterService;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Log4j2
// @RequiredArgsConstructor
@RestController
@RequestMapping("/api/letter")
public class LetterController {
    private final LetterService letterService;

    public LetterController(LetterService letterService) {
        this.letterService = letterService;
    }

    // 1. 기본 API 테스트 엔드포인트
    @GetMapping
    public ResponseEntity<String> getLetterApiStatus() {
        return ResponseEntity.ok("Letter API OK");
    }

    // 2. 받은 쪽지 조회
    @GetMapping("/{receiverId}")
    public ResponseEntity<?> getReceivedLetters(@PathVariable Long receiverId) {
        List<LetterResponseDto> letters = letterService.getReceivedLetters(receiverId);
        log.info(receiverId);
        return ResponseEntity.ok(letters);
    }

    // 3. 쪽지 전송 (POST 방식)
    @PostMapping("/send")
    public ResponseEntity<?> sendLetter(@RequestBody LetterRequestDto letterDto) {
        LetterRequestDto savedLetter = letterService.sendLetter(
            letterDto.getSenderId(), 
            letterDto.getReceiverId(), 
            letterDto.getContent()
        );
        return ResponseEntity.ok(savedLetter);
    }
    
    // private final LetterService letterService;

    // public LetterController(LetterService letterService) {
    //     this.letterService = letterService;
    // }
 
    // @GetMapping("/{receiverId}")
    // public ResponseEntity<List<Letter>> getReceivedLetters(@PathVariable Long receiverId) {
    //     List<Letter> letters = letterService.getReceivedLetters(receiverId);
    //     return ResponseEntity.ok(letters);
    // }
    
    // }

    // @PostMapping("/send/post")
    // public ResponseEntity<Letter> sendLetter(@RequestBody LetterRequestDto letterDto) {
    //     Letter savedLetter = letterService.sendLetter(
    //         letterDto.getSenderId(), letterDto.getReceiverId(), letterDto.getContent()
    //     );
    //     return ResponseEntity.ok(savedLetter);
    // }
}
    
    // @GetMapping
    // public ResponseEntity<String> getLetter() {
    //     return ResponseEntity.ok ("Letter API OK");
    // }
    // @PostMapping("/send")
    // public ResponseEntity<LetterResponseDto> sendLetter(@RequestBody LetterRequestDto  req) {
    //     LetterEntity letter = letterService.sendLetter(
    //         req.getSender(),
    //         req.getReceiver(),
    //         req.getContent()
    //     );
    //     return ResponseEntity.ok(new LetterResponseDto(letter));
    // }
        
