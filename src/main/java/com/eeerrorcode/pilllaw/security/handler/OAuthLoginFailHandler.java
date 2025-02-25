package com.eeerrorcode.pilllaw.security.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.eeerrorcode.pilllaw.dto.member.LoginHistoryDto;
import com.eeerrorcode.pilllaw.entity.member.LoginResult;
import com.eeerrorcode.pilllaw.entity.member.MemberAccount;
import com.eeerrorcode.pilllaw.security.util.CustomWebAuthenticationDetails;
import com.eeerrorcode.pilllaw.service.member.LoginHistoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;

@Log4j2
public class OAuthLoginFailHandler extends SimpleUrlAuthenticationFailureHandler{
  @Autowired
  private LoginHistoryService historyService;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    
    log.info("============!!!! OAuth2 login fail !!!! ==============");
    log.info(exception.getMessage());
    
    CustomWebAuthenticationDetails details = new CustomWebAuthenticationDetails(request);
    
    // OAuth 인증 실패 정보 추출
    String provider = getOAuthProvider(request);
    String errorCode = getErrorCode(exception);
    String failureReason = getFailureReason(exception);
    
    log.info("Failed OAuth login attempt for provider => {}", provider);
    log.info("Error code => {}", errorCode);
    
    LoginResult result = determineLoginResult(exception);
    String clientMsg = getClientMessage(result);
    
    // 로그인 이력 저장
    LoginHistoryDto loginHistoryDto = LoginHistoryDto.builder()
      .loginTime(LocalDateTime.now())
      .ip(details.getIp())
      .device(details.getDevice())
      .loginResult(result)
      .failureReason(failureReason)
      .provider(provider) // OAuth 제공자 정보 추가
      .loginType(MemberAccount.SOCIAL) // 로그인 타입 추가
      .build();
    
    historyService.register(loginHistoryDto);
    
    // 응답 생성
    response.setStatus(401);
    response.setContentType("application/json;charset=utf-8");
    JSONObject json = new JSONObject();
    json.put("code", result);
    json.put("message", exception.getMessage());
    json.put("clientMsg", clientMsg);
    
    response.getWriter().print(json);
  }
  
  // OAuth 제공자 정보 추출
  private String getOAuthProvider(HttpServletRequest request) {
    String referer = request.getHeader("Referer");
    if (referer != null) {
      if (referer.contains("google")) return "GOOGLE";
      if (referer.contains("naver")) return "NAVER";
      if (referer.contains("kakao")) return "KAKAO";
    }
    
    // URL에서 provider 추출 시도
    String requestURI = request.getRequestURI();
    if (requestURI.contains("google")) return "GOOGLE";
    if (requestURI.contains("naver")) return "NAVER";
    if (requestURI.contains("kakao")) return "KAKAO";
    
    return "UNKNOWN";
  }
  
  // OAuth 에러 코드 추출
  private String getErrorCode(AuthenticationException exception) {
    if (exception instanceof OAuth2AuthenticationException) {
      OAuth2Error error = ((OAuth2AuthenticationException) exception).getError();
      return error.getErrorCode();
    }
    return "UNKNOWN_ERROR";
  }
  
  // 실패 원인 메시지 생성
  private String getFailureReason(AuthenticationException exception) {
    if (exception instanceof OAuth2AuthenticationException) {
      OAuth2Error error = ((OAuth2AuthenticationException) exception).getError();
      return error.getDescription() != null ? error.getDescription() : error.getErrorCode();
    }
    return exception.getMessage();
  }
  
  // 로그인 결과 결정
  private LoginResult determineLoginResult(AuthenticationException exception) {
    if (exception instanceof OAuth2AuthenticationException) {
      OAuth2Error error = ((OAuth2AuthenticationException) exception).getError();
      String errorCode = error.getErrorCode();
      
      if (errorCode.equals("invalid_token")) {
        return LoginResult.INVALID_TOKEN;
      } else if (errorCode.equals("access_denied")) {
        return LoginResult.ACCESS_DENIED;
      } else if (errorCode.equals("user_denied")) {
        return LoginResult.USER_DENIED;
      } else if (errorCode.equals("authorization_request_not_found")) {
        return LoginResult.REQUEST_NOT_FOUND;
      }
    }
    
    return LoginResult.OAUTH_ERROR;
  }
  
  // 클라이언트 메시지 생성
  private String getClientMessage(LoginResult result) {
    switch (result) {
      case INVALID_TOKEN:
        return "소셜 로그인 토큰이 유효하지 않습니다.";
      case ACCESS_DENIED:
        return "소셜 로그인 접근이 거부되었습니다.";
      case USER_DENIED:
        return "사용자가 소셜 로그인 권한을 거부했습니다.";
      case REQUEST_NOT_FOUND:
        return "소셜 로그인 요청을 찾을 수 없습니다.";
      case OAUTH_ERROR:
      default:
        return "소셜 로그인 중 오류가 발생했습니다.";
    }
  }
}
