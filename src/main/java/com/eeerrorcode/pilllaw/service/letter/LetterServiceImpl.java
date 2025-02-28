package com.eeerrorcode.pilllaw.service.letter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.letter.LetterRequestDto;
import com.eeerrorcode.pilllaw.dto.letter.LetterResponseDto;
import com.eeerrorcode.pilllaw.entity.follow.Letter;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.repository.MemberRepository;
import com.eeerrorcode.pilllaw.repository.letter.LetterRepository;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class LetterServiceImpl implements LetterService {
  private final LetterRepository repository;
  private final MemberRepository memberRepository;
  
  @Override
  public LetterRequestDto sendLetter(Long senderId, Long receiverId, String content) {
    Member sender = memberRepository.findById(senderId)
      .orElseThrow(() -> new RuntimeException("Sender not found"));
    Member receiver = memberRepository.findById(receiverId)
      .orElseThrow(() -> new RuntimeException("Receiver not found"));
      
    LetterRequestDto dto = LetterRequestDto.builder()
      .receiverId(receiverId)
      .senderId(senderId)
      .content(content)
      .build();
    
    repository.save(requestDtoToEntity(dto));
    return dto;
  }
  
  @Override
  public List<LetterResponseDto> getReceivedLetters(long receiverId) {
    Member receiver = memberRepository.findById(receiverId)
      .orElseThrow(() -> new RuntimeException("Receiver not found"));
    
    List<Letter> letters = repository.findByReceiverId(receiver);
    return letters.stream()
      .filter(letter -> !letter.isDeletedByReceiver()) // 삭제되지 않은 쪽지만 반환
      .map(this::entityToResponseDto)
      .collect(Collectors.toList());
  }
  
  @Override
  public List<LetterResponseDto> getSentLetters(long senderId) {
    Member sender = memberRepository.findById(senderId)
      .orElseThrow(() -> new RuntimeException("Sender not found"));
    
    List<Letter> letters = repository.findBySenderId(sender);
    return letters.stream()
      .filter(letter -> !letter.isDeletedBySender()) // 삭제되지 않은 쪽지만 반환
      .map(this::entityToResponseDto)
      .collect(Collectors.toList());
  }
  
  @Override
  public LetterResponseDto getLetter(Long letterId) {
    Letter letter = repository.findById(letterId)
      .orElseThrow(() -> new RuntimeException("Letter not found"));
    
    // 쪽지를 읽었으므로 readAt 시간 업데이트
    if (letter.getReadAt() == null) {
      letter.setReadAt(LocalDateTime.now());
      repository.save(letter);
    }
    
    return entityToResponseDto(letter);
  }
  
  @Override
  public void deleteReceivedLetter(Long letterId) {
    Letter letter = repository.findById(letterId)
      .orElseThrow(() -> new RuntimeException("Letter not found"));
    
    letter.setDeletedByReceiver(true);
    repository.save(letter);
    
    // 양쪽 모두 삭제한 경우 물리적으로 삭제
    if (letter.isDeletedBySender() && letter.isDeletedByReceiver()) {
      repository.delete(letter);
    }
  }
  
  @Override
  public void deleteSentLetter(Long letterId) {
    Letter letter = repository.findById(letterId)
      .orElseThrow(() -> new RuntimeException("Letter not found"));
    
    letter.setDeletedBySender(true);
    repository.save(letter);
    
    // 양쪽 모두 삭제한 경우 물리적으로 삭제
    if (letter.isDeletedBySender() && letter.isDeletedByReceiver()) {
      repository.delete(letter);
    }
  }
}