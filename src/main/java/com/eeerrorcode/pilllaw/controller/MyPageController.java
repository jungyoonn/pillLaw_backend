package com.eeerrorcode.pilllaw.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.dto.common.CommonResponseDto;
import com.eeerrorcode.pilllaw.dto.member.*;
import com.eeerrorcode.pilllaw.service.follow.FollowService;
import com.eeerrorcode.pilllaw.service.member.*;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;


@RestController
@Log4j2
@RequestMapping("/api/member/mypage")
public class MyPageController {
  @Autowired
  private MemberService memberService;
  @Autowired 
  private SocialMemberService socialMemberService;
  @Autowired
  private FollowService followService;
  @Autowired
  private MemberAddressService addressService;
  @Autowired
  private PasswordEncoder encoder;

  @GetMapping("/myinfo/{mno}")
  public ResponseEntity<?> getInfo(@PathVariable("mno") String mno) {
    if(mno == null) {
      return ResponseEntity.notFound().build();
    }
    Long reqMno = Long.valueOf(mno);

    Optional<MemberDto> memberOptional = memberService.get(reqMno);
    Optional<SocialMemberDto> socialOptional = socialMemberService.getByMno(reqMno);
    Optional<AddressDto> addressOptional = addressService.getByMnoAndDefaultAddr(reqMno, true);
    MyInfoDto infoDto = new MyInfoDto();

    // 팔로잉과 팔로워 숫자 받아오기
    infoDto.setFollower(followService.getReceiver_Mno(Long.valueOf(reqMno)).size());
    infoDto.setFollowing(followService.getSender_Mno(Long.valueOf(reqMno)).size());
    
    // 기본배송지 정보
    infoDto.setAddressDto(addressOptional.orElse(null));

    // 소셜 회원
    if(socialOptional.isPresent()) {
      SocialMemberDto socialDto =socialOptional.get(); 
      String nickname = memberOptional.get().getNickname();
      if(nickname != null) {
        socialDto.setNickname(nickname);
      }
      infoDto.setSocialDto(socialDto);
      return ResponseEntity.ok(infoDto);
    }

    // 일반 회원인 경우
    if (memberOptional.isPresent()) {
      MemberDto memberDto = memberOptional.get();
      infoDto.setMemberDto(memberDto);
    }
    return ResponseEntity.ok(infoDto);
  }

  @PutMapping("modify/{mno}")
  public ResponseEntity<?> modify(@RequestBody ModifyInfoDto dto, @PathVariable("mno") String mno) {
    log.info("수정된 나의 정보 => {}", dto);

    Long reqMno = Long.valueOf(mno);
    if(memberService.get(reqMno).isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    if(dto.getSocialDto() != null ) {
      // 바뀐 닉 있으면 멤버 가져와서 저장
      if(dto.getSocialDto().getNickname() != null) {
        MemberDto memberDto = memberService.get(reqMno).get();
        memberDto.setNickname(dto.getSocialDto().getNickname());
        memberService.modify(memberDto);
      }
    } else if(dto.getMemberDto() != null) {
      MemberDto memberDto = memberService.get(reqMno).get();
  
      if (!encoder.matches(dto.getConfirmPassword(), memberDto.getPassword())) {
        return ResponseEntity.ok(
          CommonResponseDto.builder()
            .msg("비밀번호가 일치하지 않습니다.")
            .ok(false)
            .build()
        );
      }
  
      dto.getMemberDto().setPassword(encoder.encode(dto.getConfirmPassword()));
      memberService.modify(dto.getMemberDto());
    }

    if (dto.getAddressDto() != null) {
      addressService.modify(dto.getAddressDto());
    }

    return ResponseEntity.ok("정보 수정 완료!");
  }
  
}
