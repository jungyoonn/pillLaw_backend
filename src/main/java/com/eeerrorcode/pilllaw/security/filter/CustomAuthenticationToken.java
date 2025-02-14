package com.eeerrorcode.pilllaw.security.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
  private final String rawPassword;

  public String getRawPassword() {
    return rawPassword;
  } 

  public CustomAuthenticationToken(Object principal, Object credentials, String rawPassword) {
    super(principal, credentials);
    this.rawPassword = rawPassword;

    log.info(":::::: CustomAuthenticationToken 동작했음 ::::::: rawPassword = " + rawPassword);
  }
  
}
