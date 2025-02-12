package com.eeerrorcode.pilllaw.controller;

import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.dto.LetterRequestDto;
import com.eeerrorcode.pilllaw.entity.follow.Letter;
import com.eeerrorcode.pilllaw.service.letter.LetterService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




// @RequiredArgsConstructor
@RestController
@RequestMapping("/api/letter")
public class LetterController {
    
    // private final LetterService letterService;

    // public LetterController(LetterService letterService) {
    //     this.letterService = letterService;
    // }
    // @GetMapping("/send/{send}")
    // @GetMapping("/send")
    // public ResponseEntity<String> testSendLetter() {
    //     letterService.sendLetter("딸기쿠키", "치킨", "안녕?");
    //     return ResponseEntity.ok(" 쪽지가 전송되었습니다.");
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
        
