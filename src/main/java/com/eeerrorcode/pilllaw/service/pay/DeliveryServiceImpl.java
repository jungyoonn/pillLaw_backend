package com.eeerrorcode.pilllaw.service.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeerrorcode.pilllaw.dto.pay.DeliveryDto;
import com.eeerrorcode.pilllaw.entity.member.MemberAddress;
import com.eeerrorcode.pilllaw.entity.order.Order;
import com.eeerrorcode.pilllaw.entity.pay.Delivery;
import com.eeerrorcode.pilllaw.repository.member.MemberAddressRepository;
import com.eeerrorcode.pilllaw.repository.order.OrderRepository;
import com.eeerrorcode.pilllaw.repository.pay.DeliveryRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class DeliveryServiceImpl implements DeliveryService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private MemberAddressRepository memberAddressRepository;

  @Autowired
  private DeliveryRepository deliveryRepository;

  @Transactional
  @Override
  public DeliveryDto createDelivery(Long ono, Long addrno, String trackingNumber) {
    Order order = orderRepository.findById(ono)
        .orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));

    MemberAddress address = memberAddressRepository.findById(addrno)
        .orElseThrow(() -> new IllegalArgumentException("Invalid address ID"));

    // trackingNumber 기본값을 null로 설정
    Delivery delivery = Delivery.builder()
        .order(order)
        .address(address)
        .trackingNumber(null) // 처음엔 null
        .deliveryStatus(Delivery.DeliveryStatus.READY)
        .build();

    Delivery savedDelivery = deliveryRepository.save(delivery);
    return toDto(savedDelivery);
  }
}
