package com.eeerrorcode.pilllaw.repository.follow;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeerrorcode.pilllaw.entity.follow.Follow;
import com.eeerrorcode.pilllaw.entity.member.Member;

public interface FollowRepository extends JpaRepository <Follow, Long> {
  List<Follow> findByReceiverMno(long mno);
}
