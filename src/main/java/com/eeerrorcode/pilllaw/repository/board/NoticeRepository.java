package com.eeerrorcode.pilllaw.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeerrorcode.pilllaw.entity.board.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long>{
  
}
