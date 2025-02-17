package com.eeerrorcode.pilllaw.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eeerrorcode.pilllaw.security.util.JWTUtil;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.security.core.Authentication;

@Log4j2
public class SignCheckFilter extends OncePerRequestFilter {
  private AntPathMatcher antPathMatcher;
  private String pattern;
  private JWTUtil jwtUtil;
  
  public SignCheckFilter(String pattern, JWTUtil jwtUtil) {
    antPathMatcher = new AntPathMatcher();
    this.pattern = pattern;
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if(antPathMatcher.match(pattern, request.getRequestURI())){
      log.info("================== filter on ====================");
      log.info(request.getRequestURI());

      if(request.getRequestURI().equals("/api/member/signin")) {
        log.info("Skipping token validation for login request");
        filterChain.doFilter(request, response);
        return;
      }

      boolean checkHeader = checkAuthHeader(request);

      if(checkHeader) {
        // SecurityConfig에서 anyRequest().authenticated()이려면 이렇게 하는 것이 좋다
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.validateAndExtract(token);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
        return;
      } else {
        response.setStatus(403);
        response.setContentType("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        String message = "FAIL CHECK API TOKEN";
        json.put("code", 403);
        json.put("message", message);

        response.getWriter().print(json);
      }
      return; // 기존 동작 방지
    }
    filterChain.doFilter(request, response);
  }
  
  private boolean checkAuthHeader(HttpServletRequest request) {
    boolean checkResult = false;
    String authHeader = request.getHeader("Authorization");

    if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
      log.info("=========== Authorization exist :: " + authHeader + " =============");
      String email;
      try {
        email = jwtUtil.validateAndExtract(authHeader.substring(7));
        log.info("valid email ::: " + email);
        checkResult = email.length() > 0;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return checkResult;
  }
}
