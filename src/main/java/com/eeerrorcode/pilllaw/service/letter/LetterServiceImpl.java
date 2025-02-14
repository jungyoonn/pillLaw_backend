package com.eeerrorcode.pilllaw.service.letter;

import java.time.LocalDateTime;
import java.util.List;
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
public class LetterServiceImpl implements LetterService{
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
    public List<LetterResponseDto> getReceivedLetters(long mno) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'getReceivedLetters'");
    }
    @Override
    public void deleteReceivedLetter(LetterResponseDto letterDto) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'deleteReceivedLetter'");
    }
    @Override
    public void deleteSendLetter(LetterRequestDto letterDto) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'deleteSendLetter'");
    }

    

    
    // @Override
    // public Letter sendLetter(LetterRequestDto letterDto) {
    //   Letter letter = sendLetter(letterDto.getSenderId(), letterDto.getReceiverId(), letterDto.getContent());
    //   return sendLetter(letterDto.getSenderId(), letterDto.getReceiverId(), letterDto.getContent());
    // }
    // @Override
    // public Letter sendLetter(Long senderId, Long receiverId, String content) {
    //   Member sender = memberRepository.findById(senderId)
    //   .orElseThrow(() -> new RuntimeException("Sender not found"));
    //   Member receiver = memberRepository.findById(receiverId)
    //   .orElseThrow(() -> new RuntimeException("Receiver not found"));
      
    //   Letter letter = Letter.builder()
    //   .senderId(sender)
    //   .receiverId(receiver)
    //   .content(content)
    //   .sentAt(LocalDateTime.now())
    //   .deletedBySender(false)
    //   .deletedByReceiver(false)
    //   .build();
      
    //   return repository.save(letter);
    // }


  }
  
