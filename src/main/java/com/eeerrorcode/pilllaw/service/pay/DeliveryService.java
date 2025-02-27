package com.eeerrorcode.pilllaw.service.pay;

import com.eeerrorcode.pilllaw.dto.pay.DeliveryDto;
import com.eeerrorcode.pilllaw.entity.member.MemberAddress;
import com.eeerrorcode.pilllaw.entity.order.Order;
import com.eeerrorcode.pilllaw.entity.pay.Delivery;

public interface DeliveryService {
  DeliveryDto createDelivery(Long ono, Long addrno, String trackingNumber);

  default Delivery toEntity(DeliveryDto deliveryDto) {
    return Delivery.builder()
        .dno(deliveryDto.getDno())
        .order(Order.builder().ono(deliveryDto.getOno()).build())
        .address(MemberAddress.builder().addrno(deliveryDto.getAddrno()).build())
        .trackingNumber(deliveryDto.getTrackingNumber())
        .deliveryStatus(deliveryDto.getDeliveryStatus())
        .build();
  }

  default DeliveryDto toDto(Delivery delivery) {
    return DeliveryDto.builder()
    .dno(delivery.getDno())
    .ono(delivery.getOrder().getOno())
    .addrno(delivery.getAddress().getAddrno())
    .trackingNumber(delivery.getTrackingNumber())
    .deliveryStatus(delivery.getDeliveryStatus())
    .regdate(delivery.getRegDate())
    .moddate(delivery.getModDate())
    .build();
  }
}
