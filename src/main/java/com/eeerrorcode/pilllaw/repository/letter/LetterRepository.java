package com.eeerrorcode.pilllaw.repository.letter;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eeerrorcode.pilllaw.dto.letter.LetterResponseDto;
import com.eeerrorcode.pilllaw.entity.follow.Letter;
import com.eeerrorcode.pilllaw.entity.member.Member;


@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {

    List<Letter> findByReceiverId(Member receiverId);
      List<LetterResponseDto> getReceivedLetters(long receiverId);  
}
    
    
