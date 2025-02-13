package com.eeerrorcode.pilllaw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.service.member.MemberService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/member")
public class AuthController {
  @Autowired
  private MemberService service;

  @PostMapping("/email/verification-requests")
  public ResponseEntity<?> sendMail(@RequestParam("email") @Valid String email) {
    service.sendCodeToEmail(email);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/emails/verifications")
  public ResponseEntity<?> verificationEmail(@RequestParam("email") @Valid String email, @RequestParam("code") String authCode) {
    boolean response = service.verifiedCode(email, authCode);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
