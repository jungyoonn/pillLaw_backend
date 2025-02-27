package com.eeerrorcode.pilllaw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.dto.member.AddressDto;
import com.eeerrorcode.pilllaw.service.member.MemberAddressService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class MemberAddressController {

  @Autowired
  private MemberAddressService addressService;

  @GetMapping("/{mno}")
  public ResponseEntity<List<AddressDto>> getUserAddresses(@PathVariable("mno") Long mno) {
    List<AddressDto> addresses = addressService.getAddressesByMno(mno);
    return ResponseEntity.ok(addresses);
  }

  @PostMapping("/")
  public ResponseEntity<?> saveAddress(@RequestBody AddressDto addressDto) {
    try {
      // // 1️⃣ 중복 주소 체크
      // if (addressService.isDuplicateAddress(addressDto)) {
      //   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 등록된 주소입니다.");
      // }

      // 2️⃣ 주소 저장
      Long addrno = addressService.register(addressDto);

      // 3️⃣ 주소 ID 반환
      return ResponseEntity.ok(addrno);
    } catch (Exception e) {
      log.error("주소 저장 실패", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주소 저장 실패");
    }
  }
}
