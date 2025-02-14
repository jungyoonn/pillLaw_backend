package com.eeerrorcode.pilllaw.service.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.order.OrderItemDto;
import com.eeerrorcode.pilllaw.entity.order.OrderItem;
import com.eeerrorcode.pilllaw.repository.order.OrderItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
  @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Long addOrderItem(OrderItemDto orderItemDto) {
        OrderItem orderItem = toEntity(orderItemDto);
        return orderItemRepository.save(orderItem).getOino();
    }

    @Override
    public List<OrderItemDto> getOrderItemsByOrder(Long ono) {
        return orderItemRepository.findByOrderOno(ono).stream().map(this::toDto).toList();
    }

    @Override
    public void removeOrderItem(Long oino) {
        orderItemRepository.deleteById(oino);
    }
}
