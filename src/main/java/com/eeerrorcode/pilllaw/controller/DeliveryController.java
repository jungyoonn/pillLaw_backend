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
  public ResponseEntity<DeliveryDto> createDelivery(@RequestParam("ono") Long ono, @RequestParam("addrno") Long addrno, @RequestParam("trackingNumber") String trackingNumber) {
    try {
      DeliveryDto delivery = deliveryService.createDelivery(ono, addrno, trackingNumber);
      return ResponseEntity.ok(delivery);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(null);
    }
  }
}