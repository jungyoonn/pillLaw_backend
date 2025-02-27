package com.eeerrorcode.pilllaw.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
}