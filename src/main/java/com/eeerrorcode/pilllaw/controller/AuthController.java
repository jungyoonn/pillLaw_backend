package com.eeerrorcode.pilllaw.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eeerrorcode.pilllaw.security.dto.LoginDto;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/api/member")
public class AuthController {
  // @Autowired
  // private AuthenticationManager authenticationManager;
  // @Autowired
  // private JWTUtil jwtUtil;

  @PostMapping("/signin")
  public ResponseEntity<?> signin(@RequestBody LoginDto loginDto) {
    log.info("로그인 시도 : {}", loginDto.getEmail() );

    // Authentication authentication = authenticationManager.authenticate(
    //   new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
    // );

    // AuthMemberDto user = (AuthMemberDto) authentication.getPrincipal();
    // String token = jwtUtil.generateToken(user.getEmail());
    // log.info("토큰 생성 => {}", token);

    return ResponseEntity.ok(loginDto);
  }
}
