package com.eeerrorcode.pilllaw.security.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.eeerrorcode.pilllaw.dto.member.LoginHistoryDto;
import com.eeerrorcode.pilllaw.dto.order.CartDto;
import com.eeerrorcode.pilllaw.entity.member.LoginResult;
import com.eeerrorcode.pilllaw.entity.member.MemberAccount;
import com.eeerrorcode.pilllaw.security.dto.AuthMemberDto;
import com.eeerrorcode.pilllaw.security.dto.LoginDto;
import com.eeerrorcode.pilllaw.security.util.CustomWebAuthenticationDetails;
import com.eeerrorcode.pilllaw.security.util.JWTUtil;
import com.eeerrorcode.pilllaw.service.member.LoginHistoryService;
import com.eeerrorcode.pilllaw.service.order.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Log4j2
public class CustomLoginFilter extends AbstractAuthenticationProcessingFilter {
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final JWTUtil jwtUtil;

  @Autowired
  private LoginHistoryService historyService;

  @Autowired
  private CartService cartService;

  public CustomLoginFilter(String url, JWTUtil jwtUtil) {
    super(url);
    this.jwtUtil = jwtUtil;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException {
    log.info("======================= login filter on ========================");
    
    try {
      LoginDto dto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
      // LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
      
      if (dto.getEmail() == null || dto.getPassword() == null) {
        throw new AuthenticationException("Email and password are required") {};
      }

      request.setAttribute("attemptEmail", dto.getEmail());
      log.info("Attempting login with email => {}", dto.getEmail());

      log.info("password => {}", dto.getPassword());

      UsernamePasswordAuthenticationToken authenticationToken = 
        new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
      
      CustomWebAuthenticationDetails details = new CustomWebAuthenticationDetails(request);
      authenticationToken.setDetails(details);

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
    
    saveLoginHistory(authResult);
    AuthMemberDto authMember = (AuthMemberDto) authResult.getPrincipal();
    Long mno = authMember.getMno();
    
    try {
      String email = authResult.getName();
      String token = jwtUtil.generateToken(email);

      // 🔹 장바구니가 있는지 확인 후 없으면 생성(여기가 추가한 부분입니다)
        Optional<CartDto> existingCart = cartService.getCartByMember(mno);
        if (existingCart.isEmpty()) {
            log.info("mno={} 장바구니 없음. 새로 생성합니다.", mno);
            CartDto newCart = new CartDto();
            newCart.setMno(mno);
            Long newCartId = cartService.addCart(newCart);
            log.info("새 장바구니 생성 완료: cno={}", newCartId);
        } else {
            log.info("mno={} 기존 장바구니 존재. cno={}", mno, existingCart.get().getCno());
        } //여기까지
      
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      
      Map<String, String> responseMap = new HashMap<>();
      responseMap.put("token", token);
      responseMap.put("mno", mno.toString());
      log.info("mno => {}", mno);
      
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

  private void saveLoginHistory(Authentication authentication) {
    CustomWebAuthenticationDetails details = 
      (CustomWebAuthenticationDetails) authentication.getDetails();

    AuthMemberDto authMember = (AuthMemberDto) authentication.getPrincipal();

    LoginHistoryDto loginHistoryDto = LoginHistoryDto.builder()
      .loginTime(LocalDateTime.now())
      .ip(details.getIp())
      .device(details.getDevice())
      .loginResult(LoginResult.SUCCESS)
      .loginType(MemberAccount.NORMAL)
      .mno(authMember.getMno())  // Member 엔티티 참조 필요
      .build();
    
    historyService.register(loginHistoryDto);
  }
}