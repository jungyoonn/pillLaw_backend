package com.eeerrorcode.pilllaw.service.order;

import java.util.List;

import com.eeerrorcode.pilllaw.dto.order.CartItemDto;

public interface PaymentService {
    public void processOrderItems(Long ono, List<CartItemDto> cartItems);
}
