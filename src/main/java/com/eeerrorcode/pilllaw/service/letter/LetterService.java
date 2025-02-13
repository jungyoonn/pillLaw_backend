package com.eeerrorcode.pilllaw.service.letter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.letter.LetterRequestDto;
import com.eeerrorcode.pilllaw.dto.letter.LetterResponseDto;
import com.eeerrorcode.pilllaw.entity.follow.Letter;
import com.eeerrorcode.pilllaw.repository.MemberRepository;
import com.eeerrorcode.pilllaw.repository.letter.LetterRepository;

import jakarta.transaction.Transactional;
import lombok.Builder;

@Service
// @Builder
public interface LetterService {
    void sendLetter(LetterRequestDto letterDto);
    Letter sendLetter(Long senderId, Long receiverId, String content);
    List<Letter> getReceivedLetters(long mno);
    void deleteLetter(long letterId, long mno);
}


    // @Autowired
    // private final LetterRepository letterRepository;
    // @Autowired
    // private final MemberRepository memberRepository;

    // LetterService(LetterRepository letterRepository, MemberRepository memberRepository);
    
    // @Transactional
    //     public default List<Letter> getReceivedLetters(Long receiverId) {
    //         // receiverId(Long)를 받아서 Member 객체 조회
    //         // Member receiver = memberRepository.findById(receiverId)
    //         //     .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receiver not found"));
        
    //         // Member 객체를 기준으로 검색
    //         // 
    //         return letterRepository.findByReceiverId(receiverId);
    //     }

    // public Letter sendLetter(Long senderId, Long receiverId, String content) {
    //   throw new UnsupportedOperationException("Unimplemented method 'sendLetter'");
    // }
    

