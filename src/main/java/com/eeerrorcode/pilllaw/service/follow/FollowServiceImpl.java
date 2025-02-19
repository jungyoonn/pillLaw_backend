package com.eeerrorcode.pilllaw.service.follow;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.entity.follow.Follow;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.repository.follow.FollowRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
// @AllArgsConstructor
// @NoArgsConstructor
public class FollowServiceImpl implements FollowService {
  
  @Autowired
  private FollowRepository repository;

  // public FollowServiceImpl(FollowRepository repository) {
  //     this.repository = repository;
  // }
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
      return repository.findByReceiver_Mno(senderMno); 
  }

  @Override
  public void insertFollow(long receiver, long sender) {

    // is follow 불린값 처리 어떻게 할건지
    
    Follow follow = Follow
    .builder()
      .receiver(Member.builder().mno(receiver).build())
      .sender(Member.builder().mno(sender).build())
    .build();



    repository.save(follow);
  }

  
  // @Override
  // public List<Follow> getReceiverFollowList(long receiverId) {
  //     // return repository.findByReceiverFollowId(receiverId);
  //     return null;
  // }

}