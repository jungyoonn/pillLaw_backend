package com.eeerrorcode.pilllaw.service.order;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeerrorcode.pilllaw.dto.order.OrderItemDto;
import com.eeerrorcode.pilllaw.entity.order.Cart;
import com.eeerrorcode.pilllaw.entity.order.CartItem;
import com.eeerrorcode.pilllaw.entity.order.Order;
import com.eeerrorcode.pilllaw.entity.order.OrderItem;
import com.eeerrorcode.pilllaw.entity.product.Product;
import com.eeerrorcode.pilllaw.repository.order.CartItemRepository;
import com.eeerrorcode.pilllaw.repository.order.CartRepository;
import com.eeerrorcode.pilllaw.repository.order.OrderItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartService cartService;

    @Transactional
    @Override
    public List<Long> addOrderItems(Long mno, Long ono) {
        // 1. mno로 Cart 찾기
        Cart cart = cartRepository.findByMemberMno(mno)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for member: " + mno));

        // 2. CartItem 목록 가져오기
        List<CartItem> cartItems = cartItemRepository.findByCartCno(cart.getCno());

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("No items in cart for member: " + mno);
        }

        // 3. OrderItem 저장 (변환 로직을 서비스 내에서 처리)
        List<OrderItem> orderItems = Optional.ofNullable(cartItems)
                .orElse(Collections.emptyList()) // cartItems가 null이면 빈 리스트 사용
                .stream()
                .filter(Objects::nonNull) // null 값이 있을 가능성이 있다면 추가
                .map((CartItem cartItem) -> OrderItem.builder() // 타입을 명확히 지정
                        .order(Order.builder().ono(ono).build()) // 주문 ID 연결
                        .product(Product.builder().pno(cartItem.getProduct().getPno()).build()) // 상품 ID 연결
                        .price(cartItem.getPrice())
                        .subday(cartItem.getSubday())
                        .quantity(cartItem.getQuantity())
                        .build())
                .collect(Collectors.toList()); // List<OrderItem>을 명확하게 지정

        // 4. OrderItem들을 저장
        orderItemRepository.saveAll(orderItems);

        // 5. 장바구니 자체 삭제 (옵션: 장바구니 항목 삭제 후 장바구니를 삭제)
        cartService.removeCart(cart.getCno()); // 장바구니 자체 삭제

        // 6. 생성된 OrderItem의 ID를 반환
        return orderItems.stream().map(OrderItem::getOino).collect(Collectors.toList());
    }

    @Override
    public List<OrderItemDto> getOrderItemsByOrder(Long ono) {
        return orderItemRepository.findByOrderOno(ono).stream().map(this::toDto).toList();
    }

    @Override
    public int removeOrderItem(Long oino) {
        orderItemRepository.deleteById(oino);
        return 1;
    }
}
