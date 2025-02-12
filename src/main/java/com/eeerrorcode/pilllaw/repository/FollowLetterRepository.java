package com.eeerrorcode.pilllaw.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eeerrorcode.pilllaw.entity.Letter;
import com.eeerrorcode.pilllaw.entity.member.Member;

@Repository
public interface FollowLetterRepository extends JpaRepository<Member, Long> {

    List<Letter> findByReceiver(Member receiver);

}
    // 추가적인 커스텀 쿼리가 필요하다면 여기에 정의
    
    
