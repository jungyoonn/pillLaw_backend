package com.eeerrorcode.pilllaw.service.letter;

import java.util.List;

import com.eeerrorcode.pilllaw.entity.follow.Letter;
import com.eeerrorcode.pilllaw.repository.LetterRepository;

public class LetterServiceImpl {
  LetterRepository repository;
  public void deleteLetter(long letterId) {
     repository.findById(letterId)
    .orElseThrow(() -> new RuntimeException("쪽지를 찾지 못했습니다."));
//
    // if (letter.getSenderId().getMno() == ) {
      
    // }
  }
// implements LetterService{

  // @Override
  // public List<Letter> getReceivedLetters(Long receiverId) {
  //   // TODO Auto-generated method stub
  //   return null;
  // }

  // @Override
  // public Letter sendLetter(Long senderId, Long receiverId, String content) {
  //   // TODO Auto-generated method stub
  //   return null;
  // }
  
}
