package com.eeerrorcode.pilllaw.controller;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eeerrorcode.pilllaw.dto.common.CommonResponseDto;
import com.eeerrorcode.pilllaw.dto.follow.FollowDto;
import com.eeerrorcode.pilllaw.dto.member.MemberDto;
import com.eeerrorcode.pilllaw.entity.follow.Follow;
import com.eeerrorcode.pilllaw.repository.follow.FollowRepository;
import com.eeerrorcode.pilllaw.service.follow.FollowService;
import com.eeerrorcode.pilllaw.service.member.MemberService;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
 // 1. 기본 API 테스트 엔드포인트
 @GetMapping
 public ResponseEntity<String> getFollowApiStatus() {
     return ResponseEntity.ok("Follow API OK");
 }
 
 // 2. 팔로워 목록 조회 API (나를 팔로우하는 사람들)
 @GetMapping("/{receiverMno}")
 public ResponseEntity<List<Follow>> getFollowers(@PathVariable("receiverMno") long receiverMno) {
     List<Follow> followers = followService.getReceiver_Mno(receiverMno);
     log.info("팔로워 목록 조회 - receiverMno: " + receiverMno);
     return ResponseEntity.ok(followers);
 }
 
 // 3. 팔로잉 목록 조회 API (내가 팔로우하는 사람들)
 @GetMapping("/following/{senderMno}")
 public ResponseEntity<List<Follow>> getFollowing(@PathVariable("senderMno") long senderMno) {
     List<Follow> following = followService.getSender_Mno(senderMno);
     log.info("팔로잉 목록 조회 - senderMno: " + senderMno);
     return ResponseEntity.ok(following);
 }
 
 // 4. 맞팔 목록 조회 API
 @GetMapping("/followBack/{receiverMno}")
 public ResponseEntity<List<Follow>> getIsFollowBacks(@PathVariable("receiverMno") long receiverMno) {
     List<Follow> followBackList = followService.findByReceiver_MnoAndIsFollowBackTrue(receiverMno);
     log.info("맞팔 목록 조회 - receiverMno: " + receiverMno);
     return ResponseEntity.ok(followBackList);
 }
 
 // 5. 팔로우 관계 확인 API
 @GetMapping("/check/{senderMno}/{receiverMno}")
 public ResponseEntity<Map<String, Boolean>> checkFollowRelation(
         @PathVariable long senderMno, 
         @PathVariable long receiverMno) {
     boolean isFollowBack = followService.isFollowBack(senderMno, receiverMno);
     log.info("팔로우 관계 확인 - senderMno: " + senderMno + ", receiverMno: " + receiverMno);
     return ResponseEntity.ok(Map.of("isFollowBack", isFollowBack));
 }
 
 // 6. 팔로우 추가 API - 기존 FollowDto 사용
 @PostMapping("/add")
 public ResponseEntity<String> addFollow(@RequestBody FollowDto followDto) {
     followService.insertFollow(followDto.getReceiverFollowId(), followDto.getSenderFollowId());
     log.info("팔로우 추가 - senderFollowId: " + followDto.getSenderFollowId() + 
              ", receiverFollowId: " + followDto.getReceiverFollowId());
     return ResponseEntity.ok("Follow added successfully");
 }
 
 // 7. 팔로우 삭제 API
 @DeleteMapping("/{senderMno}/{receiverMno}")
 public ResponseEntity<String> deleteFollow(
         @PathVariable long senderMno, 
         @PathVariable long receiverMno) {
    //  followService.deleteFollow(senderMno, receiverMno);
     log.info("팔로우 삭제 - senderMno: " + senderMno + ", receiverMno: " + receiverMno);
     return ResponseEntity.ok("Follow deleted successfully");
 }
 
 // 8. 팔로우 토글 API - 새로운 엔드포인트
 @GetMapping("/toggle/{senderMno}/{receiverMno}")
 public ResponseEntity<Map<String, Object>> toggleFollow(
         @PathVariable("senderMno") long senderMno, 
         @PathVariable("receiverMno") long receiverMno) {
     boolean result = followService.toggleFollow(senderMno, receiverMno);
     log.info("팔로우 토글 - senderMno: " + senderMno + ", receiverMno: " + receiverMno);
     return ResponseEntity.ok(Map.of("success", true, "followed", result));
 }

}