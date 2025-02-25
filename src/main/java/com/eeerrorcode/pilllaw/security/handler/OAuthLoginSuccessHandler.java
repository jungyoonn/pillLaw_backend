package com.eeerrorcode.pilllaw.security.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import com.eeerrorcode.pilllaw.dto.member.LoginHistoryDto;
import com.eeerrorcode.pilllaw.entity.member.LoginResult;
import com.eeerrorcode.pilllaw.entity.member.MemberAccount;
import com.eeerrorcode.pilllaw.security.dto.AuthMemberDto;
import com.eeerrorcode.pilllaw.security.util.CustomWebAuthenticationDetails;
import com.eeerrorcode.pilllaw.security.util.JWTUtil;
import com.eeerrorcode.pilllaw.service.member.LoginHistoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  @Autowired
  private JWTUtil jwtUtil;
  @Autowired
  private LoginHistoryService historyService;
  
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    log.info("::::::::::::::");
    log.info("on authentication success!");

    
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
    log.info("oauth2user => {}", oAuth2User);
    AuthMemberDto authMemberDto = (AuthMemberDto) oAuth2User;
    saveLoginHistory(authentication, request, authMemberDto.getDto().getSocialProvider().toString());
    
    Long mno = authMemberDto.getMno();
    String email = authMemberDto.getEmail();
    String token = jwtUtil.generateToken(email);
    String targetUrl = request.getParameter("redirect_uri");

    log.info("email => {}", email);

    if (targetUrl == null || targetUrl.isEmpty()) {
      targetUrl = "http://localhost:3000/pilllaw/oauth2/redirect";  // 기본 리다이렉트 URL
    }

    // 리다이렉트 URI에 토큰과 이메일 추가
    String finalUrl = UriComponentsBuilder.fromUriString(targetUrl)
      .queryParam("token", token)
      .queryParam("email", email)
      .queryParam("mno", mno)
      .build().toUriString();

    log.info("redirectUri => {}", finalUrl);

    // 리다이렉트
    getRedirectStrategy().sendRedirect(request, response, finalUrl);

    // if (authentication instanceof CustomAuthenticationToken customAuth) {
    //   // String rawPassword = customAuth.getRawPassword();
      
    //   AuthMemberDto authMember = (AuthMemberDto) authentication.getPrincipal();
    //   List<MemberAccount> accounts = authMember.getAccounts();
    //   // boolean passwordResult = encoder.matches(rawPassword, authMember.getPassword());
    //   // log.info("비밀번호 일치 여부: " + passwordResult);

    //   // strategy.sendRedirect(request, response,  "/");
    // }
  }
  
  @Override
  protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    String targetUrl = request.getParameter("redirect_uri");
    if (targetUrl != null && !targetUrl.isEmpty()) {
      return targetUrl;
    }
    return super.determineTargetUrl(request, response, authentication);
  }

  private void saveLoginHistory(Authentication authentication, HttpServletRequest request, String provider) {
    String ip = "";
    String device = "";
  
    // 인증 상세 정보 타입 확인 및 처리
    if (request.getAttribute("WebAuthenticationDetails") instanceof CustomWebAuthenticationDetails) {
      CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) request.getAttribute("WebAuthenticationDetails");
      ip = details.getIp();
      device = details.getDevice();
    } else {
      // 기본 WebAuthenticationDetails인 경우 IP를 직접 가져옴
      ip = request.getRemoteAddr();
      device = request.getHeader("User-Agent");
    }

    AuthMemberDto authMember = (AuthMemberDto) authentication.getPrincipal();

    LoginHistoryDto loginHistoryDto = LoginHistoryDto.builder()
      .loginTime(LocalDateTime.now())
      .ip(ip)
      .device(device)
      .loginResult(LoginResult.SUCCESS)
      .provider(provider)
      .loginType(MemberAccount.SOCIAL)
      .mno(authMember.getMno())  // Member 엔티티 참조 필요
      .build();
    
    historyService.register(loginHistoryDto);
  }
}
