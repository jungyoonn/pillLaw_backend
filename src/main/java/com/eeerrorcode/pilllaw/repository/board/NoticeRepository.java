package com.eeerrorcode.pilllaw.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.eeerrorcode.pilllaw.entity.board.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long>{
    @Modifying
    @Transactional
    @Query("UPDATE Notice n SET n.count = n.count + 1 WHERE n.nno = :nno")
    void incrementViewCount(@Param("nno") Long nno);
}
