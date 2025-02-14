package com.eeerrorcode.pilllaw.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.eeerrorcode.pilllaw.dto.order.OrderDto;
import com.eeerrorcode.pilllaw.dto.order.OrderItemDto;
import com.eeerrorcode.pilllaw.entity.order.Order;
import com.eeerrorcode.pilllaw.service.order.OrderService;
import com.eeerrorcode.pilllaw.service.order.OrderItemService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;
    private OrderItemService orderItemService;

    // 주문 추가
    @PostMapping("/")
    public ResponseEntity<Long> addOrder(@RequestBody OrderDto orderDto) {
        Long ono = orderService.addOrder(orderDto);
        return ResponseEntity.ok(ono);
    }

    // 회원 번호로 주문 리스트 조회
    @GetMapping("/member/{mno}")
    public ResponseEntity<List<OrderDto>> getOrdersByMember(@PathVariable Long mno) {
        List<OrderDto> orders = orderService.getOrderByMemeber(mno);
        return ResponseEntity.ok(orders);
    }

    // 주문 번호로 단일 주문 조회
    @GetMapping("/{ono}")
    public ResponseEntity<Optional<OrderDto>> getOrderById(@PathVariable Long ono) {
        Optional<OrderDto> order = orderService.getOrderById(ono);
        return ResponseEntity.ok(order);
    }

    // 전체 주문 조회
    @GetMapping("/")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderDto> orderDtos = orders.stream().map(orderService::toDto).toList();
        return ResponseEntity.ok(orderDtos);
    }

    // 주문 삭제
    @DeleteMapping("/{ono}")
    public ResponseEntity<Void> removeOrder(@PathVariable Long ono) {
        orderService.removeOrder(ono);
        return ResponseEntity.noContent().build();
    }

    // 특정 주문의 주문 아이템 조회
    @GetMapping("/{ono}/items")
    public ResponseEntity<List<OrderItemDto>> getOrderItems(@PathVariable Long ono) {
        List<OrderItemDto> orderItems = orderItemService.getOrderItemsByOrder(ono);
        return ResponseEntity.ok(orderItems);
    }

    // 주문 아이템 추가
    @PostMapping("/{ono}/items")
    public ResponseEntity<Long> addOrderItem(@PathVariable Long ono, @RequestBody OrderItemDto orderItemDto) {
        orderItemDto.setOno(ono);
        Long oino = orderItemService.addOrderItem(orderItemDto);
        return ResponseEntity.ok(oino);
    }

    // 주문 아이템 삭제
    @DeleteMapping("/items/{oino}")
    public ResponseEntity<Void> removeOrderItem(@PathVariable Long oino) {
        orderItemService.removeOrderItem(oino);
        return ResponseEntity.noContent().build();
    }
}
