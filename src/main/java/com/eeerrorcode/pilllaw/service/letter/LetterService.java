package com.eeerrorcode.pilllaw.service.letter;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.letter.LetterRequestDto;
import com.eeerrorcode.pilllaw.dto.letter.LetterResponseDto;
import com.eeerrorcode.pilllaw.dto.member.MemberDto;
import com.eeerrorcode.pilllaw.entity.follow.Letter;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.repository.MemberRepository;
import com.eeerrorcode.pilllaw.repository.letter.LetterRepository;

import jakarta.transaction.Transactional;
import lombok.Builder;

public interface LetterService {
    LetterRequestDto sendLetter(Long senderId, Long receiverId, String content); //보내기용
    List<LetterResponseDto> getReceivedLetters(long mno); //받는 목록조회용
    
    void deleteReceivedLetter(LetterResponseDto letterDto);
    void deleteSendLetter(LetterRequestDto letterDto);

    default Letter requestDtoToEntity(LetterRequestDto dto) {
        Letter letter = Letter.builder()
        .letterId(dto.getLetterId())
          .senderId(Member.builder().mno(dto.getSenderId()).build())
          .receiverId(Member.builder().mno(dto.getReceiverId()).build())
          .content(dto.getContent())
          .build();
    
        return letter;
        
      }
    
      default LetterRequestDto entityToRequestDto(Letter letter) {
        LetterRequestDto dto = LetterRequestDto.builder()
          .letterId(letter.getLetterId())
          .senderId(letter.getSenderId().getMno())
          .receiverId(letter.getReceiverId().getMno())
          .content(letter.getContent())
          .build();
    
        return dto;
      }

      default Letter responseDtoToEntity(LetterResponseDto dto) {
        Letter letter = Letter.builder()
        .letterId(dto.getLetterId())
          .senderId(Member.builder().mno(dto.getSenderId()).build())
          .receiverId(Member.builder().mno(dto.getReceiverId()).build())
          .content(dto.getContent())
          .sentAt(dto.getSentAt())
          .readAt(dto.getReadAt())
          .deletedBySender(dto.isDeletedBySender())
          .deletedByReceiver(dto.isDeletedByReceiver())
          .build();
    
        return letter;
        
      }
    
      default LetterResponseDto entityToResponseDto(Letter letter) {
        LetterResponseDto dto = LetterResponseDto.builder()
          .letterId(letter.getLetterId())
          .senderId(letter.getSenderId().getMno())
          .receiverId(letter.getReceiverId().getMno())
          .content(letter.getContent())
          .sentAt(letter.getSentAt())
          .readAt(letter.getReadAt())
          .deletedBySender(letter.isDeletedBySender())
          .deletedByReceiver(letter.isDeletedByReceiver())
          .build();
    
        return dto;
      }

    // @Autowired
    // private final LetterRepository letterRepository;
    // @Autowired
    // private final MemberRepository memberRepository;

    // LetterService(LetterRepository letterRepository, MemberRepository memberRepository);
    
    // @Transactional
        // public List<LetterResponseDto> getReceivedLetters(long receiverId) {
        //     // receiverId(Long)를 받아서 Member 객체 조회
        //     Member receiver = memberRepository.findById(receiverId)
        //         .orElseThrow(() -> new ResponseStatusException("Receiver not found"));
        //         // .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receiver not found"));
        
        //     // Member 객체를 기준으로 검색
        //     // 
        //     return letterRepository.findByReceiverId(receiverId);
        // }
    }

    

