package com.eeerrorcode.pilllaw.service.order;

import com.eeerrorcode.pilllaw.dto.order.CartDto;
import com.eeerrorcode.pilllaw.dto.order.CartItemDto;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.order.Cart;
import com.eeerrorcode.pilllaw.entity.order.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartService {

    // 장바구니 추가
    Long addCart(CartDto cartDto);

    // 회원의 장바구니 조회
    Optional<CartDto> getCartByMember(Long mno);

    // mno를 통해 아이템 리스트 조회
    public List<CartItemDto> getItemsByMemberMno(Long mno);

    // cno를 통해 아이템 리스트 조회
    public List<CartItemDto> getItemsByCartCno(Long cno);

    // 장바구니 삭제
    int removeCart(Long cno);

    int removeAllItems(Long cno);

    // DTO -> Entity 변환
    default Cart toEntity(CartDto cartDto) {
        return Cart.builder()
                .cno(cartDto.getCno())
                .member(Member.builder().mno(cartDto.getMno()).build()) 
                .build();
    }

    // Entity -> DTO 변환
    default CartDto toDto(Cart cart) {
        return CartDto.builder()
                .cno(cart.getCno())
                .mno(cart.getMember().getMno())
                .regdate(cart.getRegDate())
                .moddate(cart.getModDate())
                .build();
    }
}
