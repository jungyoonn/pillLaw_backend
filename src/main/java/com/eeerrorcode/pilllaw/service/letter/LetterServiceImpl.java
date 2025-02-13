package com.eeerrorcode.pilllaw.service.letter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.letter.LetterRequestDto;
import com.eeerrorcode.pilllaw.dto.letter.LetterResponseDto;
import com.eeerrorcode.pilllaw.entity.follow.Letter;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.repository.letter.LetterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LetterServiceImpl implements LetterService{
    private final LetterRepository repository;

  @Override
  public void deleteLetter(long letterId, long memberId) {
      // letterId로 해당 쪽지를 찾음
      Letter letter = repository.findById(letterId)
              .orElseThrow(() -> new RuntimeException("Letter not found"));

      // 요청한 사용자가 발신자인 경우
      if (letter.getSenderId().getMno() == memberId) {
          letter.setDeletedBySender(true);  // 발신자 삭제 처리
      }
      // 요청한 사용자가 수신자인 경우
      else if (letter.getReceiverId().getMno() == memberId) {
          letter.setDeletedByReceiver(true);  // 수신자 삭제 처리
      } else {
          throw new RuntimeException("User is neither sender nor receiver of this letter");
      }
      
      
      // 변경된 상태를 DB에 저장
      repository.save(letter);
    }

    @Override
    public List<LetterResponseDto> getReceivedLetters(long mno) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void sendLetter(LetterRequestDto letterRequestDto) {
      // TODO Auto-generated method stub
      
    }


  }

