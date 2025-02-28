package com.eeerrorcode.pilllaw.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.dto.member.MemberDto;
import com.eeerrorcode.pilllaw.dto.member.SocialMemberDto;
import com.eeerrorcode.pilllaw.dto.member.UserInfoDto;
import com.eeerrorcode.pilllaw.service.follow.FollowService;
import com.eeerrorcode.pilllaw.service.member.MemberService;
import com.eeerrorcode.pilllaw.service.member.SocialMemberService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/api/member/userpage")
public class UserPageController {
  @Autowired
  private MemberService memberService;
  @Autowired 
  private SocialMemberService socialMemberService;
  @Autowired
  private FollowService followService;
  
  @GetMapping("/{mno}")
  public ResponseEntity<?> getMethodName(@PathVariable("mno") String mno) {
    log.info("mno => {}", mno);

    if(mno == null) {
      return ResponseEntity.notFound().build();
    }
    Long reqMno = Long.valueOf(mno);

    Optional<MemberDto> memberOptional = memberService.get(reqMno);
    Optional<SocialMemberDto> socialOptional = socialMemberService.getByMno(reqMno);
    UserInfoDto infoDto = new UserInfoDto();

    // 팔로잉과 팔로워 숫자 받아오기
    infoDto.setFollower(followService.getReceiver_Mno(Long.valueOf(reqMno)).size());
    infoDto.setFollowing(followService.getSender_Mno(Long.valueOf(reqMno)).size());

    // 소셜 회원
    if(socialOptional.isPresent()) {
      String nickname = memberOptional.get().getNickname();
      if(nickname != null) {
        infoDto.setNickname(nickname);
      }
    }

    // 일반 회원인 경우
    if (memberOptional.isPresent()) {
      String nickname = memberOptional.get().getNickname();
      infoDto.setNickname(nickname);
    }

    return ResponseEntity.ok(infoDto);
  }
}
