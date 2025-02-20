package com.eeerrorcode.pilllaw.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeerrorcode.pilllaw.entity.member.LoginHistory;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long>{
  
}
