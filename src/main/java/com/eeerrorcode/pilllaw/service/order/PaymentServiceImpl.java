package com.eeerrorcode.pilllaw.service.order;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeerrorcode.pilllaw.dto.order.CartItemDto;
import com.eeerrorcode.pilllaw.entity.order.Order;
import com.eeerrorcode.pilllaw.entity.order.OrderItem;
import com.eeerrorcode.pilllaw.entity.product.Product;
import com.eeerrorcode.pilllaw.repository.order.OrderItemRepository;
import com.eeerrorcode.pilllaw.repository.order.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private OrderItemRepository orderItemRepository;

  // 결제 완료 후 orderitems에 저장할 예정임~
  @Override
  @Transactional
  public void processOrderItems(Long ono, List<CartItemDto> cartItems) {
    Order order = orderRepository.findById(ono)
        .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));

    List<OrderItem> orderItems = Optional.ofNullable(cartItems)
        .orElse(Collections.emptyList())
        .stream()
        .map(cartItem -> OrderItem.builder()
            .order(order)
            .product(Product.builder().pno(cartItem.getPno()).build())
            .price(cartItem.getPrice())
            .subday(cartItem.getSubday())
            .quantity(cartItem.getQuantity())
            .build())
        .toList();

    orderItemRepository.saveAll(orderItems);
  }
}