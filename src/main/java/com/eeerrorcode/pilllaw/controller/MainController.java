package com.eeerrorcode.pilllaw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eeerrorcode.pilllaw.service.member.MemberService;

import lombok.extern.log4j.Log4j2;


@RestController
@RequestMapping("/api")
@Log4j2
public class MainController {
  @Autowired
  private MemberService memberService;

  @GetMapping("")
  public ResponseEntity<?> index(@RequestParam("email") String email) {
    log.info("email => {}", email);
    log.info("findByEmail => {}", memberService.getByEmail(email));
    return ResponseEntity.ok(memberService.getByEmail(email));
  }
  
}
