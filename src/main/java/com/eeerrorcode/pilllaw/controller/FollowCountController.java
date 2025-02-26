package com.eeerrorcode.pilllaw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.dto.follow.FollowCountDto;
import com.eeerrorcode.pilllaw.entity.follow.Follow;
import com.eeerrorcode.pilllaw.repository.follow.FollowRepository;
import com.eeerrorcode.pilllaw.service.follow.FollowService;
import com.eeerrorcode.pilllaw.service.member.MemberService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/follow")
public class FollowCountController {

  @Autowired
  private FollowService followService;
  @Autowired
  private FollowRepository repository;

  @GetMapping("/count/{mno}")
  public ResponseEntity<?> getCount(@PathVariable("mno") String mno) {
    if (mno == null) {
      return ResponseEntity.notFound().build();
    }
    Long reqMno = Long.valueOf(mno);

    FollowCountDto countDto = new FollowCountDto();
    countDto.setFollowing(followService.getSender_Mno(Long.valueOf(reqMno)).size());
    countDto.setFollower(followService.getReceiver_Mno(Long.valueOf(reqMno)).size());
    return ResponseEntity.ok(countDto);
    
  }
}
