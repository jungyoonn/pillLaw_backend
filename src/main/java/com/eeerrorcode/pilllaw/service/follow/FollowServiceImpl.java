package com.eeerrorcode.pilllaw.service.follow;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.entity.follow.Follow;
import com.eeerrorcode.pilllaw.repository.follow.FollowRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
// @AllArgsConstructor
// @NoArgsConstructor
public class FollowServiceImpl implements FollowService {
  
  // @Autowired
  // private final FollowRepository repository;

  // public FollowServiceImpl(FollowRepository repository) {
  //     this.repository = repository;
  // }
  // receiver 팔로우 목록 가져오기
  @Override
  public List<Follow> getFollowers(long receiverId) {
    // return repository.findBySenderFollowId(receiverId);
    return null;
  }
  
  // sender 팔로우 목록 가져오기
  @Override
  public List<Follow> getSenderFollowList(long senderId) {
      // return repository.findBySenderFollowId(senderId);
      return null;
  }
  @Override
  public List<Follow> getReceiverFollowList(long receiverId) {
      // return repository.findByReceiverFollowId(receiverId);
      return null;
  }

}