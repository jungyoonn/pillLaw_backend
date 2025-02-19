package com.eeerrorcode.pilllaw.service.follow;

import java.util.List;

import com.eeerrorcode.pilllaw.dto.follow.FollowDto;
import com.eeerrorcode.pilllaw.entity.follow.Follow;

public interface FollowService {
  List<Follow> getFollowers(long receiverId);

  List<Follow> getSenderFollowList(long senderId);

  List<Follow> getReceiverFollowList(long receiverId);
}
