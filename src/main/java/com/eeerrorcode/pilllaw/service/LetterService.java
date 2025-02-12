package com.eeerrorcode.pilllaw.service;

// import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.eeerrorcode.pilllaw.entity.follow.Letter;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.repository.FollowLetterRepository;
import com.eeerrorcode.pilllaw.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.Builder;

@Service
@Builder
public class LetterService {
    private final FollowLetterRepository letterRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public LetterService(FollowLetterRepository letterRepository, MemberRepository memberRepository) {
        this.letterRepository = letterRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public List<Letter> getReceivedLetters(Long receiverId) {
        // receiverId(Long)를 받아서 Member 객체 조회
        Member receiver = memberRepository.findById(receiverId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receiver not found"));
    
        // Member 객체를 기준으로 검색
        List<Letter> letters = letterRepository.findByReceiver(receiver);
        
        if (letters.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No letters found for receiver: " + receiverId);
        }
        return letters;
    }

    public Letter sendLetter(Long senderId, Long receiverId, String content) {
      throw new UnsupportedOperationException("Unimplemented method 'sendLetter'");
    }
    
}
