package com.eeerrorcode.pilllaw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.eeerrorcode.pilllaw.security.dto.AuthMemberDto;
import com.eeerrorcode.pilllaw.security.dto.LoginDto;
import com.eeerrorcode.pilllaw.security.util.JWTUtil;
import com.eeerrorcode.pilllaw.service.member.MemberService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/api/member")
public class AuthController {
  @Autowired
  private MemberService service;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JWTUtil jwtUtil;

  // @RequestMapping(value = "/signin", method = RequestMethod.OPTIONS)
  // public ResponseEntity<?> handleOptions() {
  //   return ResponseEntity.ok().build();
  // }

  @PostMapping("/signin")
  public ResponseEntity<?> signin(@RequestBody LoginDto loginDto) {
    log.info("로그인 시도 : {}", loginDto.getEmail() );

    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
    );

    AuthMemberDto user = (AuthMemberDto) authentication.getPrincipal();
    String token = jwtUtil.generateToken(user.getEmail());
    log.info("토큰 생성 => {}", token);

    return ResponseEntity.ok(loginDto);
  }

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
