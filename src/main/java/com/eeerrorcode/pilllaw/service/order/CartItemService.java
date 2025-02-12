package com.eeerrorcode.pilllaw.service.order;

import com.eeerrorcode.pilllaw.dto.order.CartItemDto;
import com.eeerrorcode.pilllaw.entity.order.Cart;
import com.eeerrorcode.pilllaw.entity.order.CartItem;
import com.eeerrorcode.pilllaw.entity.product.Product;


public interface CartItemService {

    // 장바구니 아이템 추가
    Long addCartItem(CartItemDto cartItemDto);

    // 장바구니 아이템 조회
    CartItemDto getCartItem(Long cino);

    // 장바구니 아이템 수정
    int updateCartItem(CartItemDto cartItemDto);

    // 장바구니 아이템 삭제
    int removeCartItem(Long cino);

    // DTO -> Entity 변환
    default CartItem toEntity(CartItemDto cartItemDto) {
        return CartItem.builder()
                .cino(cartItemDto.getCino())
                .cart(Cart.builder().cno(cartItemDto.getCno()).build()) // Cart 엔티티 설정
                .product(Product.builder().pno(cartItemDto.getPno()).build()) // Product 엔티티 설정
                .quantity(cartItemDto.getQuantity())
                .subday(cartItemDto.getSubday())
                .build();
    }

    // Entity -> DTO 변환
    default CartItemDto toDto(CartItem cartItem) {
        return CartItemDto.builder()
                .cino(cartItem.getCino())
                .cno(cartItem.getCart().getCno())
                .pno(cartItem.getProduct().getPno())
                .quantity(cartItem.getQuantity())
                .subday(cartItem.getSubday())
                .build();
    }
}
