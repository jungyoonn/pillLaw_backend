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

  @GetMapping("")
  public ResponseEntity<?> index(@RequestParam("mno") String mno, @RequestParam("email") String email) {
    log.info("mno => {}", mno);
    log.info("findById => {}", memberService.get(Long.valueOf(mno)));

    Optional<MemberDto> memberDto = memberService.get(Long.valueOf(mno));
    Optional<SocialMemberDto> socialDto = socialMemberService.getByMno(Long.valueOf(mno));

    // 소셜 회원
    if(socialDto.isPresent()) {
      return ResponseEntity.ok(socialDto.get());
    }

    // 일반 회원인 경우
    if (memberDto.isPresent()) {
      MemberDto dto = memberDto.get();
      return ResponseEntity.ok(dto);
    }
    
    return ResponseEntity.notFound().build();
  }
    
}
  

