package com.eeerrorcode.pilllaw.service.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeerrorcode.pilllaw.dto.member.AddressDto;
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

  // @Override
  // @Transactional(readOnly = true)
  // public DeliveryDto getDeliveryByOrderNumber(Long ono) {
  // Delivery delivery = deliveryRepository.findByOrderOno(ono)
  // .orElseThrow(() -> new IllegalArgumentException("배송 정보가 존재하지 않습니다."));

  // return toDto(delivery);
  // }

  @Override
  @Transactional(readOnly = true)
  public DeliveryDto getDeliveryByOrderNumber(Long ono) {
    // Delivery 조회
    Delivery delivery = deliveryRepository.findByOrderOno(ono)
        .orElseThrow(() -> new IllegalArgumentException("배송 정보가 존재하지 않습니다."));

    // MemberAddress 조회 (addrno로 AddressDto 변환)
    MemberAddress address = memberAddressRepository.findById(delivery.getAddress().getAddrno())
        .orElseThrow(() -> new IllegalArgumentException("주소 정보가 존재하지 않습니다."));

    // AddressDto 생성
    AddressDto addressDto = AddressDto.builder()
        .addrno(address.getAddrno())
        .recipient(address.getRecipient())
        .tel(address.getTel())
        .postalCode(address.getPostalCode())
        .roadAddress(address.getRoadAddress())
        .detailAddress(address.getDetailAddress())
        .defaultAddr(address.isDefaultAddr())
        .mno(address.getMember().getMno())
        .build();

    // DeliveryDto 반환
    return DeliveryDto.builder()
        .dno(delivery.getDno())
        .ono(delivery.getOrder().getOno()) // Order에서 주문 번호 가져오기
        .addrno(delivery.getAddress().getAddrno()) // addrno를 Delivery에서 가져오기
        .trackingNumber(delivery.getTrackingNumber())
        .deliveryStatus(delivery.getDeliveryStatus())
        .regdate(delivery.getRegDate())
        .moddate(delivery.getModDate())
        .address(addressDto) // AddressDto 설정
        .build();
  }
}
