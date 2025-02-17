package com.eeerrorcode.pilllaw.security.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.eeerrorcode.pilllaw.security.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Log4j2
public class CustomLoginFilter extends AbstractAuthenticationProcessingFilter {
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final JWTUtil jwtUtil;

  public CustomLoginFilter(String url, JWTUtil jwtUtil) {
    super(url);
    this.jwtUtil = jwtUtil;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException {
    log.info("======================= login filter on ========================");
    
    try {
      LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
      
      if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
        throw new AuthenticationException("Email and password are required") {};
      }

      log.info("password => {}", loginRequest.getPassword());

      UsernamePasswordAuthenticationToken authenticationToken = 
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
      
      return getAuthenticationManager().authenticate(authenticationToken);
    } catch (IOException e) {
      log.error("Failed to parse JSON request", e);
      throw new AuthenticationException("Invalid request format") {};
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
    FilterChain chain, Authentication authResult) throws IOException {
    log.info("=============== custom Login filter ===================");
    
    try {
      String email = authResult.getName();
      String token = jwtUtil.generateToken(email);
      
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      
      Map<String, String> responseMap = new HashMap<>();
      responseMap.put("token", token);
      
      String jsonResponse = objectMapper.writeValueAsString(responseMap);
      response.getWriter().write(jsonResponse);
      
      log.info("Generated token for user: {}", email);
    } catch (Exception e) {
      log.error("Token generation failed", e);
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("error", "Token generation failed");
      response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
  }

  @Getter
  @Setter
  static class LoginRequest {
    private String email;
    private String password;
  }
}