package com.eeerrorcode.pilllaw.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.dto.letter.LetterRequestDto;
import com.eeerrorcode.pilllaw.dto.letter.LetterResponseDto;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.repository.MemberRepository;
import com.eeerrorcode.pilllaw.service.letter.LetterService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
// @RequiredArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping("/api/letter")
public class LetterController {
  private final LetterService letterService;
    private final MemberRepository memberRepository;

//   public LetterController(LetterService letterService) {
//       this.letterService = letterService;
//   }


  // 1. 기본 API 테스트 엔드포인트
  @GetMapping
  public ResponseEntity<String> getLetterApiStatus() {
      return ResponseEntity.ok("Letter API OK");
  }

  // 2. 받은 쪽지 조회
  @GetMapping("/received/{receiverId}")
    public ResponseEntity<?> getReceivedLetters(@PathVariable("receiverId") Long receiverId) {
    List<LetterResponseDto> letters = letterService.getReceivedLetters(receiverId);
    letters.forEach(l -> l.setNickName(memberRepository.findById(l.getSenderId()).get().getNickname()));
    log.info("받은 쪽지 조회 - receiverId: " + receiverId);
    return ResponseEntity.ok(letters);
    }


  // 3. 보낸 쪽지 조회
  @GetMapping("/send/{senderId}")
  public ResponseEntity<?> getSentLetters(@PathVariable("senderId") Long senderId) {
      List<LetterResponseDto> letters = letterService.getSentLetters(senderId);
      log.info("보낸 쪽지 조회 - senderId: " + senderId);
      return ResponseEntity.ok(letters);
  }

  // 4. 단일 쪽지 상세 조회
  @GetMapping("/letterselectview/{letterId}")
  public ResponseEntity<?> getLetter(@PathVariable("letterId") Long letterId) {
      LetterResponseDto letter = letterService.getLetter(letterId);
      log.info("쪽지 상세 조회 - letterId: " + letterId);
      return ResponseEntity.ok(letter);
  }

  // 5. 쪽지 전송
  @PostMapping("/send")
  public ResponseEntity<?> sendLetter(@RequestBody LetterRequestDto letterDto) {
      LetterRequestDto savedLetter = letterService.sendLetter(
          letterDto.getSenderId(), 
          letterDto.getReceiverId(), 
          letterDto.getContent()
      );
      return ResponseEntity.ok(savedLetter);
  }

  // 6. 받은 쪽지 삭제 (수신자 측에서만 삭제 표시)
  @PutMapping("/delete/receiver/{letterId}")
  public ResponseEntity<?> deleteReceivedLetter(@PathVariable("letterId") Long letterId) {
      letterService.deleteReceivedLetter(letterId);
      return ResponseEntity.ok("Received letter marked as deleted");
  }

  // 7. 보낸 쪽지 삭제 (발신자 측에서만 삭제 표시)
  @PutMapping ("/delete/sender/{letterId}")
  public ResponseEntity<?> deleteSentLetter(@PathVariable("letterId") Long letterId) {
      letterService.deleteSentLetter(letterId);
      return ResponseEntity.ok("Sent letter marked as deleted");
  }
}
      
