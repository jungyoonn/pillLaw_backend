package com.eeerrorcode.pilllaw.service.follow;

import java.util.List;

// import com.eeerrorcode.pilllaw.dto.follow.FollowDto;
import com.eeerrorcode.pilllaw.entity.follow.Follow;

public interface FollowService {
  // 특정 사용자의 팔로워 목록 조회 (나를 팔로우한 사람들)
  List<Follow> getReceiver_Mno(long receiverMno);
  // 특정 사용자의 팔로잉 목록 조회 (내가 팔로우한 사람들)
  List<Follow> getSender_Mno(long senderMno);
  // 팔로잉  (내가 다른 사람을 팔로우)
  void insertFollow(long receiver, long sender);
  // 맞팔로우 업데이트 (서로 팔로우 여부 확인 후 업데이트)
  void updateFollowBack(long senderMno, long receiverMno);
  // 맞팔 여부 확인 (true/false 반환)
  boolean isFollowBack(long senderMno, long receiverMno); 
  // void deleteFollow(long senderMno, long receiverMno); // 나중에 block할때 필요한것.

  boolean toggleFollow(long senderMno, long receiverMno);

  // 맞팔 목록 조회 (isFollowBack이 true인 경우)
  List<Follow> findByReceiver_MnoAndIsFollowBackTrue(long receiverMno);
  
}
