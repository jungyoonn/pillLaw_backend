package com.eeerrorcode.pilllaw.service.order;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.order.OrderDto;
import com.eeerrorcode.pilllaw.entity.order.Order;
import com.eeerrorcode.pilllaw.repository.order.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  @Autowired
  public OrderRepository orderRepository;

  @Override
  public Long addOrder(OrderDto orderDto) {
    Order order = toEntity(orderDto);
    return orderRepository.save(order).getOno();
  }

  @Override
  public List<OrderDto> getOrderByMemeber(Long mno) {
    return orderRepository.findByMemberMno(mno).stream().map(this::toDto).toList();
  }

  @Override
  public Optional<OrderDto> getOrderById(Long ono) {
    return orderRepository.findById(ono).map(this::toDto);
  }

  @Override
  public List<Order> getAllOrders() {
    return orderRepository.findAll();
  }

  @Override
  public void removeOrder(Long ono) {
    orderRepository.deleteById(ono);
  }
}
