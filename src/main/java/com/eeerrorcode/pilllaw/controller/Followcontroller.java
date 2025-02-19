package com.eeerrorcode.pilllaw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eeerrorcode.pilllaw.service.follow.FollowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/follw")
public class Followcontroller {
  
  private FollowService followService;

  @Autowired
  public Followcontroller(FollowService followService) {
    this.followService = followService;
  }

  @GetMapping("/{mno}")
  public String getMethodName(@RequestParam String param) {
      return new String();
  }
  
}
