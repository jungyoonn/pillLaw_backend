package com.eeerrorcode.pilllaw.controller;

import java.util.List;
import java.util.Optional;

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
      // 중복된 주소의 addrno 확인
      Optional<Long> existingAddrno = addressService.findExistingAddrno(addressDto);

      if (existingAddrno.isPresent()) {
        log.info("중복된 주소 발견, 기존 addrno 반환: {}", existingAddrno.get());
        return ResponseEntity.ok(existingAddrno.get());
      }

      // 새로운 주소 저장
      Long addrno = addressService.register(addressDto);

      // 새 주소의 addrno 반환
      return ResponseEntity.ok(addrno);
    } catch (Exception e) {
      log.error("주소 저장 실패", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주소 저장 실패");
    }
  }

}
