package com.eeerrorcode.pilllaw.service.follow;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.entity.follow.Follow;
import com.eeerrorcode.pilllaw.repository.follow.FollowRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
public class FollowServiceImpl implements FollowService {

  private FollowRepository repository;

  public List<Follow> getRepository(long mno) {
    return repository.findByReceiverMno(mno);
  }

  @Override
  public List<Follow> getFollowers(long mno) {
    throw new UnsupportedOperationException("Unimplemented method 'getFollowers'");
  }

}
