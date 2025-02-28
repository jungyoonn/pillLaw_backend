package com.eeerrorcode.pilllaw.service.letter;

import java.util.List;


import com.eeerrorcode.pilllaw.dto.letter.LetterRequestDto;
import com.eeerrorcode.pilllaw.dto.letter.LetterResponseDto;
import com.eeerrorcode.pilllaw.entity.follow.Letter;
import com.eeerrorcode.pilllaw.entity.member.Member;


public interface LetterService {
    // 1. 쪽지 보내기
    LetterRequestDto sendLetter(Long senderId, Long receiverId, String content);
    
    // 2. 받은 쪽지 목록 조회
    List<LetterResponseDto> getReceivedLetters(long receiverId);
    
    // 3. 보낸 쪽지 목록 조회 (추가 필요)
    List<LetterResponseDto> getSentLetters(long senderId);
    
    // 4. 단일 쪽지 상세 조회 (추가 필요)
    LetterResponseDto getLetter(Long letterId);
    
    // 5. 받은 쪽지 삭제 (수정 필요)
    void deleteReceivedLetter(Long letterId);
    
    // 6. 보낸 쪽지 삭제 (수정 필요)
    void deleteSentLetter(Long letterId);
    
    // 변환 메서드들 (유지)
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
}