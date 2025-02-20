package com.eeerrorcode.pilllaw.security.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

@Getter
public class CustomWebAuthenticationDetails {
  private String ip;
  private String device;

  public CustomWebAuthenticationDetails(HttpServletRequest request) {
    // IP 주소 가져오기
    this.ip = request.getHeader("X-Forwarded-For");
    if (ip == null || ip.isEmpty()) {
      ip = request.getRemoteAddr();
    }
    
    // User-Agent 정보 가져오기
    String userAgent = request.getHeader("User-Agent");
    this.device = parseUserAgent(userAgent);
  }

  private String parseUserAgent(String userAgent) {
    if (userAgent.contains("Mobile")) {
      return "Mobile";
    } else if (userAgent.contains("Tablet")) {
      return "Tablet";
    }
    return "Desktop";
  }
}
