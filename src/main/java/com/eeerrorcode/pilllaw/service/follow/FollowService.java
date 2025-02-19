package com.eeerrorcode.pilllaw.service.follow;

import java.util.List;

import com.eeerrorcode.pilllaw.dto.follow.FollowDto;
import com.eeerrorcode.pilllaw.entity.follow.Follow;

public interface FollowService {
  List<Follow> getFollowers(long mno);
}
