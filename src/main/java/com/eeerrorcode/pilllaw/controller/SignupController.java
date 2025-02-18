package com.eeerrorcode.pilllaw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.dto.common.CommonResponseDto;
import com.eeerrorcode.pilllaw.dto.member.EmailRequestDto;
import com.eeerrorcode.pilllaw.service.member.MailServiceImpl;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/api/member/signup")
public class SignupController {
  @Autowired
  private MailServiceImpl mailService;

  @PostMapping("/email/verification-requests")
  public ResponseEntity<?> sendMail(@RequestBody EmailRequestDto dto) {
    log.info("==========================================");
    log.info(mailService.sendVerificationLink(dto.getEmail()));
    try {
      if (mailService.sendVerificationLink(dto.getEmail())) {
        return ResponseEntity.ok(
          CommonResponseDto.builder()
            .msg("이메일 인증 링크가 전송되었습니다 :: " + dto.getEmail())
            .ok(true)
            .statusCode(HttpStatus.OK.value())
            .build()
        );
      } else {
        return ResponseEntity.ok(
          CommonResponseDto.builder()
            .msg("이미 존재하는 회원입니다.")
            .ok(false)
            .statusCode(HttpStatus.OK.value())
            .build()
        );
      }
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(
        CommonResponseDto.builder()
          .msg("이메일 전송에 실패했습니다. 다시 시도해 주세요.")
          .ok(false)
          .statusCode(HttpStatus.BAD_REQUEST.value())
          .build()
      );
    }
  }

  @GetMapping("/email/verify")
  public ResponseEntity<CommonResponseDto> verifyEmail(@RequestParam("token") String token) {
    log.info("토큰 생성 여부 확인 => {}", token);
    try {
      boolean isVerified = mailService.verifyEmail(token);
      
      if (isVerified) {
        return ResponseEntity.ok(
          CommonResponseDto.builder()
            .msg("이메일 인증이 완료되었습니다.")
            .ok(true)
            .statusCode(HttpStatus.OK.value())
            .build()
        );
      } else {
        return ResponseEntity.badRequest().body(
          CommonResponseDto.builder()
            .msg("유효하지 않거나 만료된 인증 링크입니다.")
            .ok(false)
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .build()
        );
      }
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(
        CommonResponseDto.builder()
          .msg("이메일 인증 처리 중 오류가 발생했습니다.")
          .ok(false)
          .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
          .build()
      );
    }
  }
}
