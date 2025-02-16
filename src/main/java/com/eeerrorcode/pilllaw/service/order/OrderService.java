package com.eeerrorcode.pilllaw.service.order;

import java.util.List;
import java.util.Optional;

import com.eeerrorcode.pilllaw.dto.order.OrderDto;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.order.Order;

public interface OrderService {
  // 주문 추가
  Long addOrder(OrderDto orderDto);

  // mno로 주문 리스트 조회
  List<OrderDto> getOrderByMemeber(Long mno);

  // ono로 단건 주문 조회
  Optional<OrderDto> getOrderById(Long ono);

  // 전체주문 조회
  List<Order> getAllOrders();

  //주문 삭제
  int removeOrder(Long ono);

  // DTO -> Entity 변환
  default Order toEntity(OrderDto orderDto) {
    return Order.builder()
        .ono(orderDto.getOno())
        .member(Member.builder().mno(orderDto.getMno()).build())
        .name(orderDto.getName())
        .tel(orderDto.getTel())
        .request(orderDto.getRequest())
        .totalAmount(orderDto.getTotalAmount())
        .usingPoint(orderDto.getUsingPoint())
        .build();
  }

  // Entity -> DTO 변환
  default OrderDto toDto(Order order) {
    return OrderDto.builder()
        .ono(order.getOno())
        .mno(order.getMember().getMno())
        .name(order.getName())
        .tel(order.getTel())
        .request(order.getRequest())
        .totalAmount(order.getTotalAmount())
        .usingPoint(order.getUsingPoint())
        .regdate(order.getRegDate())
        .moddate(order.getModDate())
        .build();
  }
}