package com.eeerrorcode.pilllaw.service.follow;

import java.util.List;

// import com.eeerrorcode.pilllaw.dto.follow.FollowDto;
import com.eeerrorcode.pilllaw.entity.follow.Follow;

public interface FollowService {
  List<Follow> getReceiver_Mno(long receiverMno); // receiverId → receiverMno 변경
  List<Follow> getSender_Mno(long senderMno); // senderId → senderMno 변경
  void insertFollow(long receiver, long sender);
  void updateFollowBack(long senderMno, long receiverMno);
  boolean isFollowBack(long senderMno, long receiverMno); 
  
}
