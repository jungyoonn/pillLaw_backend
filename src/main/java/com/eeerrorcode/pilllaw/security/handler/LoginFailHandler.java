package com.eeerrorcode.pilllaw.security.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;

@Log4j2
public class LoginFailHandler implements AuthenticationFailureHandler{
  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {

    log.info("============!!!! login fail !!!! ==============");
    log.info(exception.getMessage());

    response.setStatus(401);
    response.setContentType("application/json;charset=utf-8");
    JSONObject json = new JSONObject();
    String message = exception.getMessage();
    json.put("code", 401);
    json.put("message", message);

    response.getWriter().print(json);
  }
  
}
