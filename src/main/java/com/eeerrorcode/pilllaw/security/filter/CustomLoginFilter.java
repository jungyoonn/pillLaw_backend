package com.eeerrorcode.pilllaw.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.eeerrorcode.pilllaw.security.handler.LoginSuccessHandler;
import com.eeerrorcode.pilllaw.security.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Log4j2
public class CustomLoginFilter extends AbstractAuthenticationProcessingFilter{
  private JWTUtil jwtUtil;

  public void setAuthenticationSuccessHandler(LoginSuccessHandler successHandler) {
    super.setAuthenticationSuccessHandler(successHandler);
  }

  public CustomLoginFilter(String url, JWTUtil jwt) {
    super(url);
    this.jwtUtil = jwt;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
  throws AuthenticationException, IOException, ServletException {
    log.info("======================= login filter on ========================");

    String username = request.getParameter("email");
    String password = request.getParameter("password");

    // CustomAuthenticationToken authRequest = new CustomAuthenticationToken(username, password, password);
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
    Authentication authentication = getAuthenticationManager().authenticate(authenticationToken);

    log.info(authentication.getPrincipal());
    return authentication;
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
    log.info("=============== Api Login filter ===================");
    log.info("================ successful Authentication :::: " + authResult + " =====================");
    log.info(authResult.getPrincipal());

    log.info("로그인 성공 - 토큰 생성 시작");
    String email = authResult.getName();
    try {
      String token = jwtUtil.generateToken(email);
      response.setContentType("text/plain");
      response.getOutputStream().write(token.getBytes());
      log.info("생성된 토큰: {}", token);
    } catch (Exception e) {
      log.error("토큰 생성 중 오류 발생", e);
    }
  }
}
