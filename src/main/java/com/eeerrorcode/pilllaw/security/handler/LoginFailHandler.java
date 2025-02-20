package com.eeerrorcode.pilllaw.security.handler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.eeerrorcode.pilllaw.dto.member.LoginHistoryDto;
import com.eeerrorcode.pilllaw.dto.member.MemberDto;
import com.eeerrorcode.pilllaw.entity.member.LoginResult;
import com.eeerrorcode.pilllaw.security.dto.LoginDto;
import com.eeerrorcode.pilllaw.security.util.CustomWebAuthenticationDetails;
import com.eeerrorcode.pilllaw.service.member.LoginHistoryService;
import com.eeerrorcode.pilllaw.service.member.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;

@Log4j2
public class LoginFailHandler implements AuthenticationFailureHandler{
  @Autowired
  private MemberService memberService;
  @Autowired
  private LoginHistoryService historyService;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {

    log.info("============!!!! login fail !!!! ==============");
    log.info(exception.getMessage());

    CustomWebAuthenticationDetails details = new CustomWebAuthenticationDetails(request);

    // 실패한 이메일은 request에서 가져오기
    String attemptEmail = (String) request.getAttribute("attemptEmail");
    log.info("Failed login attempt for email => {}", attemptEmail);
    try {
      LoginDto loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
      attemptEmail = loginDto.getEmail();
    } catch (IOException e) {
      log.error("Failed to read request body", e);
    }

    Optional<MemberDto> optional = memberService.getByEmail(attemptEmail);
    log.info("attempt email => {}", attemptEmail);
    LoginResult result = optional.isEmpty() ? LoginResult.EMAIL_NOT_FOUND : LoginResult.PASSWORD_MISMATCH;
    String failureReason = result.equals(LoginResult.EMAIL_NOT_FOUND) ? "이메일을 찾을 수 없음" : "비밀번호 불일치";
    String clientMsg = result.equals(LoginResult.EMAIL_NOT_FOUND) ? "존재하지 않는 이메일입니다." : "비밀번호가 일치하지 않습니다.";

    LoginHistoryDto loginHistoryDto = LoginHistoryDto.builder()
      .loginTime(LocalDateTime.now())
      .ip(details.getIp())
      .device(details.getDevice())
      .loginResult(result)
      .failureReason(failureReason)
      .mno(optional.isPresent() ? optional.get().getMno() : null)
      .build();

    historyService.register(loginHistoryDto);

    response.setStatus(401);
    response.setContentType("application/json;charset=utf-8");
    JSONObject json = new JSONObject();
    String message = exception.getMessage();
    json.put("code", result);
    json.put("message", message);
    json.put("clientMsg", clientMsg);

    response.getWriter().print(json);
  }
  
}
