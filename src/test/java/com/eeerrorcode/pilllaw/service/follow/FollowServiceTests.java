package com.eeerrorcode.pilllaw.service.follow;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.eeerrorcode.pilllaw.entity.follow.Follow;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.repository.MemberRepository;
import com.eeerrorcode.pilllaw.repository.MemberRepositoryTests;
import com.eeerrorcode.pilllaw.repository.follow.FollowRepository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

// package com.eeerrorcode.pilllaw.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.eeerrorcode.pilllaw.entity.follow.Follow;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@Rollback(false)
public class FollowServiceTests {
  
  @Autowired
  private FollowService followService;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private FollowRepository followRepository;

  @Test
  @Transactional
  @Rollback(false)
  public void testGetReceiverMnos() {
      List<Follow> receiverMnos = followService.getReceiver_Mno(12L);
      log.info(receiverMnos);
      // assertThat(receiverMnos).hasSize(2); // 예시로 2명이 팔로우한다고 가정
  }

  @Test
  public void testGetSenderMnos() {
      List<Follow> senderMnos = followService.getSender_Mno(13L);
      log.info(senderMnos);
      // assertThat(senderMnos).hasSize(2); // 예시로 2명이 팔로우한다고 가정
  }
  @Test
  @Transactional
  @Rollback(false)
  public void testInsert() {
    followService.insertFollow(39, 38);
  }

  //업데이트는 굳이 불필요할것 같음..
  // @Test
  // @Transactional
  // @Rollback(false)
  // public void testupdate() {
    
  //   long senderMno = 38L ;
  //   long receiverMno = 39L;

  //   followService.updateFollowBack(senderMno, receiverMno);

  //   Optional<Follow> updatedFollow = followRepository.findBySender_MnoAndReceiver_Mno(senderMno, receiverMno);

  //   updatedFollow.ifPresent(follow -> {
  //     log.info("Updated FollowBack Status: " + follow.getIsFollowBack());
  //     assertTrue(follow.getIsFollowBack());
  //   });

  //   log.info("Follow back updated test completed.");
  // }
  
  // public void followTest() {
  //   // Member 1 (팔로우하는 사람) 생성
  //   Member member1 = Member.builder()
  //     .email("test1@test.com")
  //     .password("1234") // 암호화 필요 없음
  //     .name("테스터1")
  //     .nickname("닉네임1")
  //     .tel("010-1111-1111")
  //     .build();
  //    memberRepository.save(member1);

  //   // Member 2 (팔로우 대상) 생성
  //   Member member2 = Member.builder()
  //     .email("test2@test.com")
  //     .password("1234") // 암호화 필요 없음
  //     .name("테스터2")
  //     .nickname("닉네임2")
  //     .tel("010-2222-2222")
  //     .build();
  //     memberRepository.save(member2);

  //   // Follow 관계 추가
  //   Follow follow = Follow.builder()
  //     .sender(member1) // 팔로우 요청을 보낸 사람
  //     .receiver(member2) // 팔로우 받는 사람
  //     .isFollowBack(false) // 기본적으로 false 설정
  //     .build();

  //   followRepository.save(follow);
  //   log.info("팔로우 저장 완료: " + follow.toString());
  //   // log.info(followRepository.findAll().toString());
  // }

  @Test
  @Transactional
  @Rollback(false)
  public void unfollowTest() {
    // 이미 생성된 팔로우 관계를 가져오기
    Follow follow = followRepository.findById(1L).orElse(null);
    if (follow != null) {
      followRepository.delete(follow); // 팔로우 관계 삭제
    }

    log.info(followRepository.findAll()); // 삭제 후 상태 확인
  }

  // @Test
  // @Transactional
  // @Rollback(false)
  // public void getFollowListTest() {
  //   // 특정 member의 팔로우 목록 가져오기
  //   Member member = memberRepository.findByEmail("test1@test.com").orElse(null);
  //   if (member != null) {
  //     // log.info(followRepository.findById(member)); // follower로서 팔로우 목록 가져오기
  //   }
  // }
}