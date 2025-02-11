package com.eeerrorcode.pilllaw.controller;

import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.dto.LetterRequestDto;
import com.eeerrorcode.pilllaw.entity.LetterEntity;
import com.eeerrorcode.pilllaw.service.LetterService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




// @RequiredArgsConstructor
@RestController
@RequestMapping("/api/letter")
public class LetterController {
    
    private final LetterService letterService;

    public LetterController(LetterService letterService) {
        this.letterService = letterService;
    }
    // @GetMapping("/send/{send}")
    // public ResponseEntity<String> testSendLetter() {
    //     letterService.sendLetter("딸기쿠키", "치킨", "안녕?");
    //     return ResponseEntity.ok(" 쪽지가 전송되었습니다.");
    // }
    @GetMapping("/{receiver}")
    public ResponseEntity<List<LetterEntity>> getReceivedLetters(@PathVariable String receiver) {
        // System.out.println("Received request for receiver: " + receiver);
        List<LetterEntity> letters = letterService.getReceivedLetters(receiver);
        return ResponseEntity.ok(letterService.getReceivedLetters(receiver));
}
    //     if (letters.isEmpty()) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // ✅ 결과가 없을 경우 404 반환
    //     }
    //     return ResponseEntity.ok(letters);
    // }

    @PostMapping("/send")
    public ResponseEntity<LetterEntity> sendLetter(@RequestBody LetterRequestDto letterDto) {
        LetterEntity savedLetter = letterService.sendLetter(
            letterDto.getSender(), letterDto.getReceiver(), letterDto.getContent()
        );
        return ResponseEntity.ok(savedLetter);
    }
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
        
