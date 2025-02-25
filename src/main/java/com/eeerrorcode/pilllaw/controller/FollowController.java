package com.eeerrorcode.pilllaw.controller;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eeerrorcode.pilllaw.dto.common.CommonResponseDto;
import com.eeerrorcode.pilllaw.dto.member.MemberDto;
import com.eeerrorcode.pilllaw.entity.follow.Follow;
import com.eeerrorcode.pilllaw.repository.follow.FollowRepository;
import com.eeerrorcode.pilllaw.service.follow.FollowService;
import com.eeerrorcode.pilllaw.service.member.MemberService;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/follow")
@Log4j2
public class FollowController {
  
  @Autowired
  private FollowService followService;
  @Autowired
  private MemberService memberService;
  @Autowired
  private FollowRepository repository;
 //
// 1. 팔로워 목록 조회 (나를 팔로우한 사람들)
  @GetMapping("/{mno}")
  public ResponseEntity<List<Follow>> getReceiver_Mno(@PathVariable ("mno") long receiverMno) {
    List<Follow> followList = followService.getReceiver_Mno(receiverMno);  // 팔로워 목록 가져오기
    
    return ResponseEntity.ok(followList);
  }
// 2. 팔로잉 목록 (내가 팔로우한 사람들)
  @GetMapping("/sender/{mno}")
  public ResponseEntity<List<Follow>> getSender_Mno(@PathVariable ("mno") long senderMno) {
    List<Follow> followList = followService.getSender_Mno(senderMno);  // 팔로잉 목록 가져오기
    
    return ResponseEntity.ok(followList);
  }
  // @PostMapping("/{receiverMno}/{senderMno}")
  // public ResponseEntity<String> toggleFollow(@PathVariable long receiverMno, @PathVariable long senderMno) {
  //   // 먼저, 두 사람 간에 팔로우 관계가 있는지 확인
  //   boolean isFollowBack = followService.isFollowBack(senderMno, receiverMno);
    
  //   if (isFollowBack) {
  //       // 이미 팔로우 관계가 있다면 팔로우 취소
  //       followService.deleteFollow(senderMno, receiverMno);
  //       return ResponseEntity.ok("팔로우 취소되었습니다.");
  //   } else {
  //       // 팔로우 관계가 없다면 팔로우 추가
  //       followService.insertFollow(receiverMno, senderMno);
  //       followService.updateFollowBack(senderMno, receiverMno);
  //       return ResponseEntity.ok("팔로우 되었습니다.");
  //   }
  // } 

  @GetMapping("/{receiverMno}/{senderMno}")
  public ResponseEntity<?> toggleFollow(@PathVariable long receiverMno, @PathVariable long senderMno) {
    // boolean isFollowBack = followService.isFollowBack(senderMno, receiverMno);
    boolean isFollow = followService.toggleFollow(senderMno, receiverMno);
    log.info(isFollow);
    if (isFollow) {
      // 이미 팔로우 관계가 있다면 팔로우 취소
      // followService.deleteFollow(senderMno, receiverMno);
      return ResponseEntity.ok("팔로우 취소되었습니다.");
    } else {
      // 팔로우 관계가 없다면 팔로우 추가
      // followService.insertFollow(receiverMno, senderMno);
      // followService.updateFollowBack(senderMno, receiverMno);
      return ResponseEntity.ok("팔로우 되었습니다.");
    }

    // return ResponseEntity.ok(isFollow);
  }
  

   // 맞팔 목록 조회 API
   @GetMapping("/followBack/{receiverMno}")
   public ResponseEntity<List<Follow>> getIsFollowBacks(@PathVariable long receiverMno) {
       List<Follow> followBackList = followService.findByReceiver_MnoAndIsFollowBackTrue(receiverMno);
       return ResponseEntity.ok(followBackList);
   }
  
    //   //ex) senderMno=40&receiverMno=38 
    // mno로 닉네임 가져오기
    @GetMapping("/nickname/{mno}")
    public ResponseEntity<?> getNickname(@PathVariable Long mno) {
      // System.out.println("Received mno:" + mno);
    Optional<MemberDto> optional = memberService.get(mno);
    
    if (optional.isEmpty()) {
      // System.out.println("해당 mno 없음!");
      return ResponseEntity.ok(CommonResponseDto.builder()
      .msg("존재하지 않는 회원입니다.")
      .ok(false)
      .statusCode(HttpStatus.OK.value())
      .build()
      );
    }
    
    MemberDto dto = optional.get();
    // System.out.println("찾지못함" + dto.getNickname());
    String nickname = dto.getNickname();  // mno로 닉네임 조회
    return ResponseEntity.ok(nickname);
  }
  
}


// @GetMapping("/check")
// public ResponseEntity<String> checkFollowStatus(@RequestParam(required = false, defaultValue = "0") long senderMno, 
//                                                 @RequestParam(required = false, defaultValue = "0") long receiverMno) {
//   boolean isFollowBack = followService.isFollowBack(receiverMno, senderMno);
//     // 로그 추가
//     System.out.println("Sender MNO: " + senderMno + ", Receiver MNO: " + receiverMno);
//     System.out.println("isFollowBack result: " + isFollowBack);
    
//   if (isFollowBack) {
//     return ResponseEntity.ok("맞팔로우");
//   } 
//   else {
//     return ResponseEntity.ok("맞팔로우 하기");
//   }

  
// }