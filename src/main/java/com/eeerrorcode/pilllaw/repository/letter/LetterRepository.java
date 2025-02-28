package com.eeerrorcode.pilllaw.repository.letter;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eeerrorcode.pilllaw.dto.letter.LetterResponseDto;
import com.eeerrorcode.pilllaw.entity.follow.Letter;
import com.eeerrorcode.pilllaw.entity.member.Member;


@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {
    // 받은 쪽지 조회
    List<Letter> findByReceiverId(Member receiverId);
    
    // 보낸 쪽지 조회 (추가 필요)
    List<Letter> findBySenderId(Member senderId);
}
    
