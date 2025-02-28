package com.eeerrorcode.pilllaw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eeerrorcode.pilllaw.dto.pay.DeliveryDto;
import com.eeerrorcode.pilllaw.service.pay.DeliveryService;

@RestController
@RequestMapping("/api/v1/delivery")
public class DeliveryController {

  @Autowired
  private DeliveryService deliveryService;

  @PostMapping("/create")
  public ResponseEntity<DeliveryDto> createDelivery(@RequestBody DeliveryDto deliveryDto) {
      return ResponseEntity.ok(deliveryService.createDelivery(
          deliveryDto.getOno(),
          deliveryDto.getAddrno(),
          deliveryDto.getTrackingNumber()
      ));
  }

  // @GetMapping("/{ono}")
  // public ResponseEntity<DeliveryDto> getDeliveryByOrderNumber(@PathVariable("ono") Long ono) {
  //     DeliveryDto deliveryDto = deliveryService.getDeliveryByOrderNumber(ono);
  //     return ResponseEntity.ok(deliveryDto);
  // }

  @GetMapping("/{ono}")
    public ResponseEntity<DeliveryDto> getDeliveryByOrderNumber(@PathVariable("ono") Long ono) {
        try {
            // Delivery 정보 조회
            DeliveryDto deliveryDto = deliveryService.getDeliveryByOrderNumber(ono);
            return ResponseEntity.ok(deliveryDto);
        } catch (IllegalArgumentException e) {
            // 예외 처리: 배송 정보가 없을 경우 404 Not Found 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}