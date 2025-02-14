package com.eeerrorcode.pilllaw.security.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.eeerrorcode.pilllaw.security.handler.LoginSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Log4j2
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter{
  public CustomLoginFilter(AuthenticationManager authenticationManager) {
    super.setAuthenticationManager(authenticationManager);
  }

  public void setAuthenticationSuccessHandler(LoginSuccessHandler successHandler) {
    super.setAuthenticationSuccessHandler(successHandler);
  }

  // public CustomLoginFilter(String url) {
  //   super(url);
  // }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
  throws AuthenticationException {
    log.info("======================= login filter on ========================");

    String username = request.getParameter("email");
    String password = request.getParameter("password");

    CustomAuthenticationToken authRequest = new CustomAuthenticationToken(username, password, password);
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
    Authentication authentication = getAuthenticationManager().authenticate(authenticationToken);

    log.info(authentication.getPrincipal());
    return authentication;
  }
}
