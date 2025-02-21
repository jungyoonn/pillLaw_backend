package com.eeerrorcode.pilllaw.security.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import com.eeerrorcode.pilllaw.security.util.JWTUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  @Autowired
  private JWTUtil jwtUtil;
  
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    log.info("::::::::::::::");
    log.info("on authentication success!");

    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
    String email = oAuth2User.getAttribute("email");
    String token = jwtUtil.generateToken(email);
    String targetUrl = request.getParameter("redirect_uri");

    if (targetUrl == null || targetUrl.isEmpty()) {
      targetUrl = "http://localhost:3000/pilllaw/oauth2/redirect";  // 기본 리다이렉트 URL
    }

    // 리다이렉트 URI에 토큰과 이메일 추가
    String finalUrl = UriComponentsBuilder.fromUriString(targetUrl)
      .queryParam("token", token)
      .queryParam("email", email)
      .build().toUriString();

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
}
