package com.eeerrorcode.pilllaw.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eeerrorcode.pilllaw.entity.follow.Letter;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {

    // List<Letter> findByReceiverId(Long receiverid);

}
    
    
