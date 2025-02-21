package com.eeerrorcode.pilllaw.service.follow;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.entity.follow.Follow;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.repository.follow.FollowRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
// @AllArgsConstructor
// @NoArgsConstructor
public class FollowServiceImpl implements FollowService {
  
  @Autowired
  private FollowRepository repository;

  public FollowServiceImpl(FollowRepository repository) {
      this.repository = repository;
  }
  // receiver 팔로우 목록 가져오기
  @Override
  public List<Follow> getReceiver_Mno(long receiverMno) {
    // return repository.findBySenderFollowId(receiverId);
    return repository.findByReceiver_Mno(receiverMno); 
    // return null;
  }
  
  // sender 팔로우 목록 가져오기
  @Override
  public List<Follow> getSender_Mno(long senderMno) {
    // return repository.findBySenderFollowId(senderId);
      // return null;
      return repository.findBySender_Mno(senderMno); 
  }

  @Override
  public void insertFollow(long receiverMno, long senderMno) {
      // isFollowBack 기본값은 false
      boolean isFollowBack = false;
  
      // 상대방(sender)이 이미 나(receiver)를 팔로우하는지 확인
      Optional<Follow> existingFollow = repository.findBySender_MnoAndReceiver_Mno(receiverMno, senderMno);
      
      if (existingFollow.isPresent()) {
          isFollowBack = true; // 이미 팔로우하고 있다면 맞팔로우로 설정
      }
  
      // 팔로우 관계 객체 생성
      Follow follow = Follow.builder()
              .receiver(Member.builder().mno(receiverMno).build())
              .sender(Member.builder().mno(senderMno).build())
              .isFollowBack(isFollowBack) // 팔로우 시, 기본값은 false
              .build();
  
      // 팔로우 저장
      repository.save(follow);
  }
  
  @Transactional
  public void updateFollowBack(long senderMno, long receiverMno) {
      // sender와 receiver가 팔로우하는 관계인지 확인
      Optional<Follow> followOpt = repository.findBySender_MnoAndReceiver_Mno(senderMno, receiverMno);
      
      followOpt.ifPresent(follow -> {
          // 이미 팔로우 관계가 있으면 맞팔로우 상태를 true로 업데이트
          if (!follow.getIsFollowBack()) {
          // if (!follow.IsFollowBack()) {
              follow.setIsFollowBack(true);
              repository.save(follow); // 변경 사항 저장
              // log.info("FollowBack updated: " + follow.getIsFollowBack());
          }
      });
  
  // @Override
  // public List<Follow> getReceiverFollowList(long receiverId) {
  //     // return repository.findByReceiverFollowId(receiverId);
  //     return null;
  // }
    }

  @Override
  public boolean isFollowBack(long senderMno, long receiverMno) {
        // sender -> receiver 팔로우 관계
        Optional<Follow> senderFollowsReceiver = repository.findBySender_MnoAndReceiver_Mno(senderMno, receiverMno);

        // receiver -> sender 팔로우 관계
        Optional<Follow> receiverFollowsSender = repository.findBySender_MnoAndReceiver_Mno(receiverMno, senderMno);

        // 두 관계가 모두 존재하면 팔로우백 (true)
        return senderFollowsReceiver.isPresent() && receiverFollowsSender.isPresent();
    }
}