package com.eeerrorcode.pilllaw.security.handler;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.eeerrorcode.pilllaw.entity.member.MemberAccount;
import com.eeerrorcode.pilllaw.security.dto.AuthMemberDto;
import com.eeerrorcode.pilllaw.security.filter.CustomAuthenticationToken;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoginSuccessHandler implements AuthenticationSuccessHandler{
  private RedirectStrategy strategy = new DefaultRedirectStrategy();
  
  private PasswordEncoder encoder;
  
  public LoginSuccessHandler(PasswordEncoder encoder) {
    this.encoder = encoder;
  }
  
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    log.info("::::::::::::::");
    log.info("on authentication success!");

    if (authentication instanceof CustomAuthenticationToken customAuth) {
      String rawPassword = customAuth.getRawPassword();
      
      AuthMemberDto authMember = (AuthMemberDto) authentication.getPrincipal();
      List<MemberAccount> accounts = authMember.getAccounts();
      boolean passwordResult = encoder.matches(rawPassword, authMember.getPassword());

      log.info("비밀번호 일치 여부: " + passwordResult);
    }
  }
  
}
