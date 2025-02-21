package com.eeerrorcode.pilllaw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eeerrorcode.pilllaw.entity.follow.Follow;
import com.eeerrorcode.pilllaw.repository.follow.FollowRepository;
import com.eeerrorcode.pilllaw.service.follow.FollowService;
import com.eeerrorcode.pilllaw.service.member.MemberService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/follow")
public class FollowController {
  
  @Autowired
  private FollowService followService;
  @Autowired
  private MemberService memberService;
  @Autowired
  private FollowRepository repository;
 
      // 팔로우 목록 불러오기
      @GetMapping("/{mno}")
      // public ResponseEntity<List<Follow>> getReceiver_Mno(@PathVariable long receiverMno) {
      public ResponseEntity<List<Follow>> getReceiver_Mno(@PathVariable ("mno") long receiverMno) {
          List<Follow> followList = followService.getReceiver_Mno(receiverMno);  // 팔로우 목록 가져오기
          return ResponseEntity.ok(followList);
      }
  
  
    //   //ex) senderMno=40&receiverMno=38 
      @GetMapping("/check")
      public ResponseEntity<String> checkFollowStatus(@RequestParam(required = false, defaultValue = "0") long senderMno, 
                                                      @RequestParam(required = false, defaultValue = "0") long receiverMno) {
          
          boolean isFollowBack = followService.isFollowBack(receiverMno, senderMno);
        

            // 로그 추가
          System.out.println("Sender MNO: " + senderMno + ", Receiver MNO: " + receiverMno);
          System.out.println("isFollowBack result: " + isFollowBack);
        if (isFollowBack) {
            return ResponseEntity.ok("맞팔로우");
        } else {
            return ResponseEntity.ok("맞팔로우 하기");
        }

    // // // mno로 닉네임 가져오기
    // @GetMapping("/nickname/{mno}")
    // public ResponseEntity<String> getNickname(@PathVariable Long mno) {
    //     String nickname = memberService.getNicknameByMno(mno);  // mno로 닉네임 조회
    //     return ResponseEntity.ok(nickname);
    // }
}
}
