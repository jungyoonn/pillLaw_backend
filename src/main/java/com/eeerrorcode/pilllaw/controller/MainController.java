package com.eeerrorcode.pilllaw.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eeerrorcode.pilllaw.dto.member.MemberDto;
import com.eeerrorcode.pilllaw.dto.member.SocialMemberDto;
import com.eeerrorcode.pilllaw.service.member.MemberService;
import com.eeerrorcode.pilllaw.service.member.SocialMemberService;

import lombok.extern.log4j.Log4j2;


@RestController
@RequestMapping("/api")
@Log4j2
public class MainController {
  @Autowired
  private MemberService memberService;
  @Autowired SocialMemberService socialMemberService;

  @GetMapping("/{mno}")
  public ResponseEntity<?> index(@PathVariable("mno") String mno) {
    log.info("mno => {}", mno);
    log.info("findById => {}", memberService.get(Long.valueOf(mno)));

    Optional<MemberDto> memberOptional = memberService.get(Long.valueOf(mno));
    Optional<SocialMemberDto> socialOptional = socialMemberService.getByMno(Long.valueOf(mno));

    // 소셜 회원
    if(socialOptional.isPresent()) {
      SocialMemberDto socialDto =socialOptional.get(); 
      String nickname = memberOptional.get().getNickname();
      if(nickname != null) {
        socialDto.setNickname(nickname);
      }
      return ResponseEntity.ok(socialDto);
    }

    // 일반 회원인 경우
    if (memberOptional.isPresent()) {
      MemberDto memberDto = memberOptional.get();
      return ResponseEntity.ok(memberDto);
    }
    
    return ResponseEntity.notFound().build();
  }
    
}
  

